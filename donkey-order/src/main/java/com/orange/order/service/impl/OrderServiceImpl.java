package com.orange.order.service.impl;

import com.orange.order.config.WxPayConfig;
import com.orange.order.constant.OrderStatus;
import com.orange.order.constant.PaymentType;
import com.orange.order.dao.OrderAdditionalChargesDao;
import com.orange.order.dao.OrderDao;
import com.orange.order.dao.OrderProductDao;
import com.orange.order.domain.Order;
import com.orange.order.domain.OrderAdditionalCharges;
import com.orange.order.domain.OrderProduct;
import com.orange.order.dto.OrderCommentDto;
import com.orange.order.dto.OrderDto;
import com.orange.order.dto.OrderProductDto;
import com.orange.order.info.OrderPageModel;
import com.orange.order.service.BookkeepingBookService;
import com.orange.order.service.OrderService;
import com.orange.order.vo.OrderDetailVo;
import com.orange.order.vo.OrderMgVo;
import com.orange.order.vo.OrderVo;
import com.orange.person.constant.AuthenticationGrade;
import com.orange.person.dao.UserBaseDao;
import com.orange.person.domain.UserBase;
import com.orange.share.page.SortUtil;
import com.orange.share.util.IdGeneratorUtils;
import com.orange.share.util.JsonUtil;
import com.orange.share.vo.PageVo;
import com.orange.shop.constants.DeliverType;
import com.orange.shop.constants.OptionalGradeType;
import com.orange.shop.constants.ProductSaleType;
import com.orange.shop.dao.*;
import com.orange.shop.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderProductDao orderProductDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    ProductAdditionalChargesDao productAdditionalChargesDao;
    @Autowired
    UserBaseDao userBaseDao;
    @Autowired
    BookkeepingBookService bookkeepingBookService;
    @Autowired
    ShopDao shopDao;
    @Autowired
    OrderAdditionalChargesDao chargesDao;
    @Autowired
    ProductSpecsDao productSpecsDao;
    @Autowired
    ProductCommentDao productCommentDao;
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map addOrder(OrderDto orderDto, String spbill_create_ip) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserBase userBase = userBaseDao.findOne(userId);
        log.info(userId);
        //TODO 生成唯一订单号
        String orderId= IdGeneratorUtils.nextId();
        String body="";
        Order order =new Order();
        //TODO 计算商品
        BigDecimal totalAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderProductDto productDto:orderDto.getProducts()){
            BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

            Product product= productDao.findOne(productDto.getProductId());
            if (product==null){
                throw new RuntimeException("订单的商品不存在");
            }
            OrderProduct orderProduct =new OrderProduct();
            orderProduct.setProductDecs(product.getProductDescribe());
            orderProduct.setProductSaleType(product.getProductSaleType());
            body += product.getProductName();
            //TODO 将商品数量*价格相加
            List<ProductSpecs> specsList =  productSpecsDao.findAllByProductIdAndEnable(product.getId(),true);
            if(specsList!=null&&specsList.size()>0){
                ProductSpecs productSpecs= productSpecsDao.findOne(productDto.getProductSpec());
                if (productSpecs==null){
                    log.error("商品规格错误:"+product.getId());
                    throw new RuntimeException("产品数据错误,请联系维护人员");
                }
                log.info(JsonUtil.objectToString(productSpecs));
                orderAmount=orderAmount.add(productSpecs.getPrice().multiply(new BigDecimal(productDto.getProductCount())));
                log.info("getProductCount"+orderAmount.longValue());
                orderProduct.setProductPrice(productSpecs.getPrice());
                orderProduct.setSpecsDec(productSpecs.getSpecsName());
                orderProduct.setSpecsId(productSpecs.getId());
            }
            else {
                orderAmount=orderAmount.add(product.getProductPrice().multiply(new BigDecimal(productDto.getProductCount())));
                orderProduct.setProductPrice(product.getProductPrice());
            }

            //TODO 判断是否是租赁商品 还要加上天数
            if (product.getProductSaleType()== ProductSaleType.LEASE_TYPE.getCode()){
                Long days= (orderDto.getEndTime()-orderDto.getStartTime())/(3600*24)+1;
                log.info("days"+days);
                orderAmount=orderAmount.multiply(new BigDecimal(days.intValue()));
                log.info("daysorderAmount"+orderAmount.longValue());
                //TODO 判断认证并加上押金
                if (userBase.getAuthenticationGrade()!= AuthenticationGrade.UNCERTIFIED_MANDATORY.getCode()){
                    orderAmount = orderAmount.add(product.getProductDeposit());
                    orderProduct.setAuthenticationDeposit(false);
                }else {
                    orderProduct.setAuthenticationDeposit(true);
                }
                order.setOrderType(ProductSaleType.LEASE_TYPE.getCode());
            }else {
                order.setOrderType(ProductSaleType.SALE_TYPE.getCode());
            }

            //TODO 计算商品可选附加项价格
            List<Long> chargesIds = productDto.getAdditionalChargesIds();
            for (Long chargesId:chargesIds){
                ProductAdditionalCharges additionalCharges = productAdditionalChargesDao.findOne(chargesId);
                if (additionalCharges.getOptionalGrade()!=OptionalGradeType.OPTIONAL.getCode()){
                    continue;
                }
                orderAmount=orderAmount.add(additionalCharges.getChargesPrice());
                log.info("charges1"+orderAmount.longValue());

                OrderAdditionalCharges charges = new OrderAdditionalCharges();
                charges.setOrderId(orderId);
                charges.setAdditionalChargesName(additionalCharges.getAdditionalChargesName());
                charges.setChargesPrice(additionalCharges.getChargesPrice());
                chargesDao.save(charges);
            }
            //TODO 计算商品必选附加项价格
            List<ProductAdditionalCharges> additionalCharges= productAdditionalChargesDao.findAllByProductIdAndOptionalGradeOrOptionalGrad(productDto.getProductId(), OptionalGradeType.MANDATORY.getCode(),OptionalGradeType.UNCERTIFIED_MANDATORY.getCode());
            for (ProductAdditionalCharges charges:additionalCharges){
                //TODO 判断此附加项是否需要认证免押金
                if (charges.getOptionalGrade()==OptionalGradeType.UNCERTIFIED_MANDATORY.getCode()){
                    if (userBase.getAuthenticationGrade()== AuthenticationGrade.UNCERTIFIED_MANDATORY.getCode()){
                        continue;
                    }
                }
                orderAmount=orderAmount.add(charges.getChargesPrice());
                log.info("charges2"+orderAmount.longValue());
                OrderAdditionalCharges charges2 = new OrderAdditionalCharges();
                charges2.setOrderId(orderId);
                charges2.setAdditionalChargesName(charges.getAdditionalChargesName());
                charges2.setChargesPrice(charges.getChargesPrice());
                chargesDao.save(charges2);
            }
            //TODO 加运费

            if (orderDto.getDeliverType()== DeliverType.MAIL.getCode()){
                orderAmount=orderAmount.add(product.getDeliverMoney());
                order.setDeliverMoney(product.getDeliverMoney());
            }else {
                order.setDeliverMoney(BigDecimal.ZERO);
            }

            totalAmount = totalAmount.add(orderAmount);
            orderProduct.setShopId(product.getShopId());
            orderProduct.setProductCount(productDto.getProductCount());
            orderProduct.setOrderId(orderId);
            orderProduct.setProductName(product.getProductName());
            orderProduct.setProductThumbnail(product.getProductThumbnail());
            orderProduct.setProductId(product.getId());
            orderProduct.setProductDeposit(product.getProductDeposit());
            orderProductDao.save(orderProduct);
        }
        order.setDeliverType(orderDto.getDeliverType());
        order.setId(orderId);
        BeanUtils.copyProperties(orderDto,order);
        order.setItemCount(orderDto.getProducts().size());
        order.setUserId(userId);
        order.setPayMoney(totalAmount);
        order.setTotalReward(totalAmount);
        order.setOrderMold(orderDto.getOrderMold());
        order.setOrderState(OrderStatus.UNPAID.getCode());
        order.setCreateTime(System.currentTimeMillis()/1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, 1);// 24小时制
        date = cal.getTime();
        String time_expire = format.format(date);
        Map map = new HashMap();
        order.setTimeExpire(date.getTime());
        if (orderDto.getPaymentType()== PaymentType.WX_PAID.getValue()){
            try {
                 map = bookkeepingBookService.WXPay(userId,userBase.getOpenId(), spbill_create_ip,order.getId(),totalAmount, WxPayConfig.notify_url,"易驴商品",time_expire);
                 map.put("orderId",orderId);
                 orderDao.save(order);
                log.info(JsonUtil.objectToString(order));
                return map;
            } catch (Exception e) {
                log.error("用户userId:{}失败,失败原因:{}",userId,e.getMessage());
                throw new RuntimeException("申请支付时出现异常");
            }
        }else {
            map.put("orderId","");
            return map;
        }

    }

    @Override
    public PageVo getPage(OrderPageModel orderPageModel) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Order> page = orderDao.findAll(OrderSpec.method1(userId,orderPageModel.getOrderStatus()), SortUtil.buildDESC(orderPageModel.getPage(),orderPageModel.getSize(), Sort.Direction.DESC,"createTime"));
        List<OrderVo> orderVos = new ArrayList<>();
        for (Order order: page.getContent()){
            OrderVo orderVo = new OrderVo();
            orderVo.setOrderState(order.getOrderState());
            orderVo.setPayMoney(order.getPayMoney());
            orderVo.setOrderType(order.getOrderType());
            orderVo.setEndTime(order.getEndTime());
            orderVo.setStartTime(order.getStartTime());
            orderVo.setCreateTime(order.getCreateTime());
            orderVo.setDeliverType(order.getDeliverType());
            orderVo.setOrderMold(order.getOrderMold());
            orderVo.setOrderId(order.getId());
            List<OrderProduct> orderProducts =  orderProductDao.findAllByOrderId(order.getId());
            orderVo.setOrderProducts(orderProducts);
            orderVos.add(orderVo);
        }

        return new PageVo(page.getTotalPages(),orderPageModel.getPage(),page.getTotalElements(),orderVos);
    }

    @Override
    public OrderDetailVo getOrderDetail(String id) {
        Order order = orderDao.findOne(id);
        if (order==null){
            throw new RuntimeException("订单不存在");
        }
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        BeanUtils.copyProperties(order,orderDetailVo);
        orderDetailVo.setOrderId(order.getId());
        List<OrderProduct> orderProducts =  orderProductDao.findAllByOrderId(order.getId());
        List<OrderAdditionalCharges> charges =  chargesDao.findAllByOrderId(order.getId());
        orderDetailVo.setOrderProducts(orderProducts);
        orderDetailVo.setCharges(charges);
        return orderDetailVo;
    }

    @Override
    public void cancelOrder(String orderId,Boolean flag) {
        Order order = orderDao.findOne(orderId);
        if (order.getOrderState()==OrderStatus.TO_BE_SHIPPED.getCode()){
            int res = bookkeepingBookService.WXRefund(order.getUserId(),order.getId(),order.getPayMoney(),order.getPayMoney(),WxPayConfig.refund_notify_url,order.getPayMoney());
            if (res==1){
                order.setOrderState(OrderStatus.CANCEL.getCode());
                order.setCancelTime(System.currentTimeMillis()/1000);
                orderDao.saveAndFlush(order);
            }
        } else {
            order.setOrderState(OrderStatus.CANCEL.getCode());
            order.setCancelTime(System.currentTimeMillis()/1000);

            orderDao.saveAndFlush(order);
        }
        if (flag){
            this.rabbitTemplate.convertAndSend("orderPayCancel", orderId);
        }
    }

    @Override
    public PageVo getMgPage(OrderPageModel orderPageModel) {
        Page<Order> page;
        if (StringUtils.isBlank(orderPageModel.getOrderId())){
            page = orderDao.findAll(OrderSpec.method2(orderPageModel.getOrderStatus()), SortUtil.buildDESC(orderPageModel.getPage(),orderPageModel.getSize(), Sort.Direction.DESC,"createTime"));

        }else {
            page = orderDao.findAll(OrderSpec.method3(orderPageModel.getOrderStatus(),orderPageModel.getOrderId()), SortUtil.buildDESC(orderPageModel.getPage(),orderPageModel.getSize(), Sort.Direction.DESC,"createTime"));

        }
        List<OrderMgVo> orderVos = new ArrayList<>();
        for (Order order: page.getContent()){
            OrderMgVo orderVo = new OrderMgVo();
            BeanUtils.copyProperties(order,orderVo);
            orderVo.setOrderId(order.getId());
            List<OrderProduct> orderProducts =  orderProductDao.findAllByOrderId(order.getId());
            orderVo.setOrderProducts(orderProducts);
            orderVo.setUserId(order.getUserId());
            UserBase userBase = userBaseDao.findOne(order.getUserId());
            orderVo.setNickname(userBase.getNickname());
            orderVos.add(orderVo);
        }

        return new PageVo(page.getTotalPages(),orderPageModel.getPage(),page.getTotalElements(),orderVos);
    }

    @Override
    public void confirmReceive(String orderId) {
        Order order = orderDao.findOne(orderId);
        if (order.getOrderState()==OrderStatus.RECEIVED.getCode()){
            order.setOrderState(OrderStatus.TO_BE_EVALUSATED.getCode());
            order.setReceivedTime(System.currentTimeMillis()/1000);
            orderDao.saveAndFlush(order);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void comment(OrderCommentDto commentDto) {
        Order order = orderDao.findOne(commentDto.getOrderId());
        if (order==null){
            return;
        }
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OrderProduct> orderProducts = orderProductDao.findAllByOrderId(commentDto.getOrderId());
        for(OrderProduct orderProduct : orderProducts){
            ProductComment productComment = new ProductComment();
            productComment.setEnable(true);
            productComment.setUserId(userId);
            productComment.setCreateTime(System.currentTimeMillis()/1000);
            productComment.setCommentContent(commentDto.getCommentContent());
            productComment.setImgUrl(commentDto.getImgUrl());
            productComment.setOrderId(commentDto.getOrderId());
            productComment.setProductId(orderProduct.getProductId());
            productCommentDao.save(productComment);
        }
        order.setOrderState(OrderStatus.CLOSE.getCode());
        orderDao.saveAndFlush(order);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void upOrder(String orderId, Integer status) {
        Order order = orderDao.findOne(orderId);
        switch (status){
            case 20 :
                order.setOrderState(OrderStatus.TO_BE_SHIPPED.getCode());
                order.setPayTime(System.currentTimeMillis()/1000);
                break;
            case 30 :
                order.setOrderState(OrderStatus.RECEIVED.getCode());
                order.setConsignTime(System.currentTimeMillis()/1000);
                break;
            case 40 :
                order.setOrderState(OrderStatus.TO_BE_EVALUSATED.getCode());
                order.setReceivedTime(System.currentTimeMillis()/1000);
                break;
            case 50 :
                order.setOrderState(OrderStatus.CANCEL.getCode());
                order.setCancelTime(System.currentTimeMillis()/1000);
                break;
            case 60 :
                order.setOrderState(OrderStatus.CLOSE.getCode());
                order.setCompletionTime(System.currentTimeMillis()/1000);
                break;
            default: return;
        }
        orderDao.saveAndFlush(order);
    }

    public static class OrderSpec {
        public static Specification<Order> method1(String userId, Integer status) {
            return new Specification<Order>() {
                @Override
                public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("enable");
                    Path<Integer> ui = root.get("userId");
                    Path<Integer> os = root.get("orderState");
                    Predicate predicate;
                    if (status==0){
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(ui, userId));
                    }else if(status == 100){
                        Predicate p1;
//                        Predicate p2;
                        p1 = criteriaBuilder.or(criteriaBuilder.equal(os, OrderStatus.RECEIVED.getCode()),criteriaBuilder.equal(os, OrderStatus.TO_BE_SHIPPED.getCode()));
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(ui, userId),p1);
//                        predicate = criteriaBuilder.or(p1,p2);
                    }else if(status == 200){
                        Predicate p1;
                        p1 = criteriaBuilder.or(criteriaBuilder.equal(os, OrderStatus.TO_BE_EVALUSATED.getCode()),criteriaBuilder.equal(os, OrderStatus.CLOSE.getCode()));
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(ui, userId),p1);
                    }else {
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(ui, userId),criteriaBuilder.equal(os, status));
                    }
                    return predicate;
                }
            };
        }
        public static Specification<Order> method2( Integer status) {
            return new Specification<Order>() {
                @Override
                public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("enable");
                    Path<Integer> ui = root.get("userId");
                    Path<Integer> os = root.get("orderState");
                    Predicate predicate;
                    if (status==0){
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true));
                    }else {
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(os, status));
                    }
                    return predicate;
                }
            };
        }
        public static Specification<Order> method3( Integer status,String orderId) {
            return new Specification<Order>() {
                @Override
                public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("enable");
                    Path<Integer> ui = root.get("userId");
                    Path<Integer> os = root.get("orderState");
                    Path<String>  oi = root.get("id");
                    Predicate predicate;
                    if (status==0){
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.like(oi,"%"+orderId+"%"));
                    }else {
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(os, status),criteriaBuilder.like(oi,"%"+orderId+"%"));
                    }
                    return predicate;
                }
            };
        }
    }
}

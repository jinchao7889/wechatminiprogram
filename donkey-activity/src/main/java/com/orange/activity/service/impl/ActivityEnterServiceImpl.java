package com.orange.activity.service.impl;

import com.orange.activity.constants.ActivityStatus;
import com.orange.activity.constants.EnterStatus;
import com.orange.activity.dao.ActivityDao;
import com.orange.activity.dao.ActivityEnterDao;
import com.orange.activity.domain.Activity;
import com.orange.activity.domain.ActivityEnter;
import com.orange.activity.dto.ActivityEnterDto;
import com.orange.activity.info.AcivityEnterPageInfo;
import com.orange.activity.service.ActivityEnterService;
import com.orange.activity.vo.ActivityEnterDetailVo;
import com.orange.activity.vo.ActivityEnterVo;
import com.orange.activity.vo.ActivityVo;
import com.orange.order.dao.OrderDao;
import com.orange.order.domain.Order;
import com.orange.order.service.OrderService;
import com.orange.share.page.PageInfo;
import com.orange.share.page.SortUtil;
import com.orange.share.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ActivityEnterServiceImpl implements ActivityEnterService {
    @Autowired
    ActivityEnterDao activityEnterDao;
    @Autowired
    ActivityDao activityDao;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDao orderDao;
    private final byte[] lock = new byte[0];
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map addActivityEnter(ActivityEnterDto activityEnterDto, String spbill_create_ip) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ActivityEnter activityEnter = new ActivityEnter();
        synchronized (lock){
            Map map = new HashMap<>();

            Activity activity = activityDao.findOne(activityEnterDto.getActivityId());
            if (activity.getMaxPeopleNumber()<activity.getEnrolmentPeopleNumber()+activityEnterDto.getEnterNumber()||activity.getActivityStatus()==ActivityStatus.PEOPLE_FULL.getCode()){
                map.put("activity_status",ActivityStatus.PEOPLE_FULL.getCode());
                return map;
            }else if(activity.getActivityStatus()==ActivityStatus.TIME_END.getCode()||activity.getEnrolmentEndTime()<System.currentTimeMillis()/1000){
                map.put("activity_status",ActivityStatus.TIME_END.getCode());
                return map;
            }else if (activity.getActivityStatus()==ActivityStatus.COMPLETED.getCode()){
                map.put("activity_status",ActivityStatus.COMPLETED.getCode());
                return map;
            }
            if (activity.getProductId()!=0&&activity.getProductId()!=null){
                 map =  orderService.addOrder(activityEnterDto.getOrderDto(),spbill_create_ip);
                if (StringUtils.isBlank((String)map.get("orderId")) ){
                    throw new RuntimeException("订单生成异常");
                }
                activityEnter.setOrderId((String)map.get("orderId"));
                activityEnter.setProductId(activity.getProductId());
                activityEnter.setPaymentStatus(EnterStatus.NO_PAY.getCode());
            }else {
                activityEnter.setProductId(0L);
                activityEnter.setPaymentStatus(EnterStatus.ONGOING.getCode());
            }
            map.put("activity_status",ActivityStatus.ONGOING.getCode());
            BeanUtils.copyProperties(activityEnterDto,activityEnter);
            activityEnter.setUserId(userId);
            activityEnter.setCreateTime(System.currentTimeMillis()/1000);
            activityEnter.setEnterNumber(activityEnterDto.getEnterNumber());
            activity.setEnrolmentPeopleNumber(activity.getEnrolmentPeopleNumber()+activityEnterDto.getEnterNumber());
            activityDao.saveAndFlush(activity);
            activityEnterDao.save(activityEnter);
            map.put("activity_enter_id",activityEnter.getId());
            return map;
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = "orderPaySuccess")
    @Override
    public void activityPaySuccess(String orderId) {
        try {
            activityEnterDao.updateStatus(orderId,EnterStatus.ONGOING.getCode());

        }catch (Exception e){
            log.error("订单支付成功",e);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = "orderPayCancel")
    @Override
    public void activityPayCancel(String orderId) {

        try {
            ActivityEnter activityEnter = activityEnterDao.findActivityEnterByOrderId(orderId);
            if (activityEnter==null){
                return;
            }
            activityEnter.setPaymentStatus(EnterStatus.PEOPLE_FULL.getCode());
            Activity activity = activityDao.findOne(activityEnter.getActivityId());
            int num  = activity.getEnrolmentPeopleNumber() - activityEnter.getEnterNumber();
            if (num<0){
                num = 0;
            }


            activity.setEnrolmentPeopleNumber(num);
            activityDao.saveAndFlush(activity);
            activityEnterDao.saveAndFlush(activityEnter);

        }catch (Exception e){
            log.error("取消订单罗",e);
        }
    }
    @Override
    public PageVo getPage(PageInfo pageInfo) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<ActivityEnter> page = activityEnterDao.findAllByUserId(userId, SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"createTime"));
        List<ActivityEnterVo> list = new ArrayList<>();
        for (ActivityEnter activityEnter :page.getContent()){
            Activity activity = activityDao.findOne(activityEnter.getActivityId());
            ActivityEnterVo activityEnterVo = new ActivityEnterVo();
            BeanUtils.copyProperties(activity,activityEnterVo);
            activityEnterVo.setActivityId(activity.getId());
            activityEnterVo.setId(activityEnter.getId());
            activityEnterVo.setCreateTime(activityEnter.getCreateTime());

            if ( activity.getActivityStatus() == ActivityStatus.COMPLETED.getCode()){
                activityEnterVo.setPaymentStatus(EnterStatus.PEOPLE_FULL.getCode());
            }else {
                activityEnterVo.setPaymentStatus(activityEnter.getPaymentStatus());

            }
            list.add(activityEnterVo);
        }
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),list);
    }

    @Override
    public ActivityEnterDetailVo getActivityEnter(Long id) {
        ActivityEnter activityEnter =activityEnterDao.findOne(id);
        Activity activity = activityDao.findOne(activityEnter.getActivityId());
        ActivityEnterDetailVo enterDetailVo = new ActivityEnterDetailVo();
        BeanUtils.copyProperties(activityEnter,enterDetailVo);
        enterDetailVo.setProductId(activity.getProductId());
        if ( activity.getActivityStatus() == ActivityStatus.COMPLETED.getCode()){
            enterDetailVo.setPaymentStatus(EnterStatus.PEOPLE_FULL.getCode());
        }
        return enterDetailVo;
    }

    @Override
    public PageVo getMgPage(AcivityEnterPageInfo pageInfo) {
        Page<ActivityEnter> page = activityEnterDao.findAll(ActivityEnterSpec.method1(pageInfo.getPayStatus(),pageInfo.getActivityId(),pageInfo.getRealName()),SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"createTime"));
        List<ActivityEnterDetailVo> list = new ArrayList<>();
        for (ActivityEnter activityEnter :page.getContent()){
            Activity activity = activityDao.findOne(activityEnter.getActivityId());
            ActivityEnterDetailVo enterDetailVo = new ActivityEnterDetailVo();
            BeanUtils.copyProperties(activityEnter,enterDetailVo);
            enterDetailVo.setProductId(activity.getProductId());
            enterDetailVo.setActivityTitle(activity.getTitle());
            enterDetailVo.setActivityStatus(activity.getActivityStatus());
            list.add(enterDetailVo);
        }
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),list);
    }

    @Override
    public List<ActivityEnterDetailVo> getAll(String activityId) {
        List<ActivityEnterDetailVo> list = new ArrayList<>();
        for (ActivityEnter activityEnter : activityEnterDao.findAllByActivityId(activityId)){
            Activity activity = activityDao.findOne(activityEnter.getActivityId());
            ActivityEnterDetailVo enterDetailVo = new ActivityEnterDetailVo();
            BeanUtils.copyProperties(activityEnter,enterDetailVo);
            enterDetailVo.setProductId(activity.getProductId());
            enterDetailVo.setActivityTitle(activity.getTitle());
            enterDetailVo.setActivityStatus(activity.getActivityStatus());
            list.add(enterDetailVo);
        }
        return list;
    }

    @Override
    public void cancelEnter(Long id) {
        ActivityEnter activityEnter = activityEnterDao.findOne(id);
        if (activityEnter==null){
            return;
        }
        activityEnter.setPaymentStatus(EnterStatus.PEOPLE_FULL.getCode());
        Activity activity = activityDao.findOne(activityEnter.getActivityId());
        int num  = activity.getEnrolmentPeopleNumber() - activityEnter.getEnterNumber();
        if (num<0){
            num = 0;
        }
        activity.setEnrolmentPeopleNumber(num);

        if (StringUtils.isBlank(activityEnter.getOrderId())){
            orderService.cancelOrder(activityEnter.getOrderId(),false);
        }
        activityDao.saveAndFlush(activity);
        activityEnterDao.saveAndFlush(activityEnter);
    }

    public static class ActivityEnterSpec {
        public static Specification<ActivityEnter> method1(Integer ps, String ai, String rn) {
            return new Specification<ActivityEnter>() {
                @Override
                public Predicate toPredicate(Root<ActivityEnter> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Integer> paymentStatus = root.get("paymentStatus");
                    Path<String> activityId = root.get("activityId");
                    Path<String> realName = root.get("realName");
                    Predicate predicate;
                    if (StringUtils.isBlank(rn)){
                        if (StringUtils.isBlank(ai)){
                            if (ps==0){
                                predicate = criteriaBuilder.and( );

                            }else {
                                predicate = criteriaBuilder.and( criteriaBuilder.equal(paymentStatus,ps));
                            }
                        }else {
                            if (ps==0){
                                predicate = criteriaBuilder.and( criteriaBuilder.like(activityId, ai));

                            }else {
                                predicate = criteriaBuilder.and( criteriaBuilder.like(activityId, ai),criteriaBuilder.equal(paymentStatus,ps));
                            }
                        }
                    }else {
                        if (StringUtils.isBlank(ai)){
                            if (ps==0){
                                predicate = criteriaBuilder.and( criteriaBuilder.equal(realName,rn));

                            }else {
                                predicate = criteriaBuilder.and( criteriaBuilder.equal(realName,rn),criteriaBuilder.equal(paymentStatus,ps));
                            }
                        }else {
                            if (ps==0){
                                predicate = criteriaBuilder.and( criteriaBuilder.like(activityId, ai),criteriaBuilder.equal(realName,rn));

                            }else {
                                predicate = criteriaBuilder.and( criteriaBuilder.like(activityId, ai),criteriaBuilder.equal(realName,rn),criteriaBuilder.equal(paymentStatus,ps));
                            }
                        }
                    }


                    return predicate;
                }
            };
        }
    }
}

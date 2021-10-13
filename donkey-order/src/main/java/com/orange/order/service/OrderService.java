package com.orange.order.service;

import com.orange.order.dto.OrderCommentDto;
import com.orange.order.dto.OrderDto;
import com.orange.order.info.OrderPageModel;
import com.orange.order.vo.OrderDetailVo;
import com.orange.share.vo.PageVo;

import java.util.Map;

public interface OrderService {
    Map addOrder(OrderDto orderDto, String spbill_create_ip);

    /**
     * 分页获得订单
     * @param orderPageModel
     * @return
     */
    PageVo getPage(OrderPageModel orderPageModel);

    /**
     * 分页获得订单
     * @param id
     * @return
     */
    OrderDetailVo getOrderDetail(String id);

    void cancelOrder(String orderId,Boolean flag);

    PageVo getMgPage(OrderPageModel orderPageModel);

    void confirmReceive(String orderId);

    void comment(OrderCommentDto commentDto);

    void upOrder(String orderId,Integer status);
}

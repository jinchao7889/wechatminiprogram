package com.orange.order.controller;

import com.orange.order.dto.OrderCommentDto;
import com.orange.order.dto.OrderDto;
import com.orange.order.info.OrderPageModel;
import com.orange.order.service.OrderService;
import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import com.orange.share.util.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/add")
    public ResponseWrapper add(@Validated @RequestBody OrderDto orderDto, HttpServletRequest request){
        return ResponseWrapper.markSuccess(orderService.addOrder(orderDto, IPUtil.getIpAddr(request)));
    }

    @PostMapping("/get_page")
    public ResponseWrapper getPage(@RequestBody OrderPageModel orderPageModel){
        return ResponseWrapper.markSuccess(orderService.getPage(orderPageModel));
    }

    @GetMapping("/get/{orderId}")
    public ResponseWrapper getPage(@PathVariable String orderId){
        return ResponseWrapper.markSuccess(orderService.getOrderDetail(orderId));
    }

    @GetMapping("/cancel/{orderId}")
    public ResponseWrapper cancelOrder(@PathVariable String orderId){
        orderService.cancelOrder(orderId,true);
        return ResponseWrapper.markSuccess(ReturnCode.UPDATE_SUCCESS);
    }

    @PostMapping("/mg/get_page")
    public ResponseWrapper getMgPage(@RequestBody OrderPageModel orderPageModel){
        return ResponseWrapper.markSuccess(orderService.getMgPage(orderPageModel));
    }

    @GetMapping("/confirm/receive/{orderId}")
    public ResponseWrapper getConfirmReceive(@PathVariable String orderId){
        orderService.confirmReceive(orderId);
        return ResponseWrapper.markSuccess(ReturnCode.UPDATE_SUCCESS);
    }

    @PostMapping("/comment")
    public ResponseWrapper comment(@Validated @RequestBody OrderCommentDto orderCommentDto){
        orderService.comment(orderCommentDto);
        return ResponseWrapper.markSuccess(ReturnCode.UPDATE_SUCCESS);
    }

    @GetMapping("/up/{orderId}/{status}")
    public ResponseWrapper upOrder(@PathVariable String orderId, @PathVariable Integer status){
        orderService.upOrder(orderId,status);
        return ResponseWrapper.markSuccess(ReturnCode.UPDATE_SUCCESS);
    }
}

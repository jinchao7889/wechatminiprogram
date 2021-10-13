package com.orange.order.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {

    /**
     * 备注
     */
    String remarks;

    private String contacts;
    private Integer orderMold;
    private String phone;
    /**
     * 大致地址
     */
    private String approximatelyAddress;
    /**
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 是否使用优惠券
     */
    Boolean useCoupon=false;
    /**
     * 优惠券ID
     */
    String couponId;

    /**
     * 配送方式
     */
    @NotNull
    Integer  deliverType;

    /**
     * 支付方式
     */
    @NotNull
    Integer paymentType;

    @NotEmpty
    List<OrderProductDto> products;

    /**
     * 租赁开始时间
     */
    Long startTime;
    /**
     * 租赁结束时间
     */
    Long endTime;
}

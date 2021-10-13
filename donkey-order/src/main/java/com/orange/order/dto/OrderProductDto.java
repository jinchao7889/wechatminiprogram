package com.orange.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderProductDto {
    private Integer productCount;
    private Long productId;
    /**
     * 选择的商品规格
     */
    private Long productSpec;
    /**
     * 附加收费项目
     */
    private List<Long> additionalChargesIds;
}

package com.orange.trip.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TripExpensesElementVo {
    /**
     * 对应的条目
     */
    String entry;

    /**
     * 对应的花销
     */
    BigDecimal expenses;
}

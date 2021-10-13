package com.orange.trip.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TripExpensesToVo {
    List<TripExpensesVo> list;
    BigDecimal totalMoney;
}

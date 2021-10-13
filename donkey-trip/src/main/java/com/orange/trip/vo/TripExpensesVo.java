package com.orange.trip.vo;

import com.orange.trip.domain.TripExpensesDetailed;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Data
public class TripExpensesVo {

    Integer id;
    Long tripId;
    BigDecimal totalMoney;
    /**
     * 大概时间
     */
    String tripTime;

    List<TripExpensesDetailed> expensesDetail;
}

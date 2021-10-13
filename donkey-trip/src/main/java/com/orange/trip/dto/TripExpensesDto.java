package com.orange.trip.dto;

import com.orange.trip.domain.TripExpensesDetailed;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class TripExpensesDto {
    Integer id;
    String tripTime;
    @NotNull
    Long tripId;
    BigDecimal totalMoney;
    Integer serialNumber;

    List<TripExpensesDetailed> expensesDetail;
}

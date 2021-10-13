package com.orange.trip.info;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TripExpensesDetailedInfo {
    /**
     * 行程大概的Id
     */
    @NotNull
    Integer tripSummarizeId;
    /**
     * 对应的条目
     */
    @NotBlank
    String entry;
    /**
     * 对应的花销
     */
    @NotNull
    BigDecimal expenses;
    @NotNull
    Integer serialNumber;
}

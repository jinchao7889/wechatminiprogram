package com.orange.tavels.info;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TravelsInfo {
    String id;
    /**
     * 游记的标题
     */
    String title;

    /**
     * 封面照片路径
     */
    String coverMap;


    @Max(2)
    @Min(1)
    @NotNull
    Integer travelsStatus;

    Long departureTime;

    /**
     * 旅行天数
     */
    Double travelDays;

    /**
     * 旅行的方式
     */
    String travelType;

    String travelDestination;
    BigDecimal perCapitaConsumption;


}

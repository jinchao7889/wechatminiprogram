package com.orange.trip.info;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class TripSummarizeInfo {

    Integer id;
    @NotNull
    Long tripId;
    @NotBlank
    String content;
    @NotNull
    Integer serialNumber;
    /**
     * 更正信息
     */
    @NotBlank
    String correctionsContent;
    @NotBlank
    String tripTime;
}

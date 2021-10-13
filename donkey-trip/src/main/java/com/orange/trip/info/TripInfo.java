package com.orange.trip.info;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

@Data
public class TripInfo {
    @NotBlank
    String coverMap;
    @NotBlank
    String travelsId;
}

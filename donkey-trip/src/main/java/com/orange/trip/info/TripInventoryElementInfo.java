package com.orange.trip.info;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class TripInventoryElementInfo {
    @NotNull
    Integer inventoryId;

    @NotBlank
    String inventoryElement;

    @NotNull
    Boolean isCheck;

    @NotNull
    Integer serialNumber;
}

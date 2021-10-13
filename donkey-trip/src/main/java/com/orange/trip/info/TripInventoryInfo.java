package com.orange.trip.info;

import com.orange.trip.domain.TripInventoryElement;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TripInventoryInfo {
    Integer id;
    @NotNull
    Long tripId;
    @NotBlank
    String inventoryType;
    List<TripInventoryElement> inventoryElements;
}

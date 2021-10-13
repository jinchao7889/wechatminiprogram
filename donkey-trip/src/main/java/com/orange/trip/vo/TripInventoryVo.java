package com.orange.trip.vo;

import com.orange.trip.domain.TripInventoryElement;
import lombok.Data;

import java.util.List;

@Data
public class TripInventoryVo {
    /**
     * 清单属性
     */
    Integer  id;
    String inventoryType;
    Long tripId;
    List<TripInventoryElement>   inventoryElements;
}

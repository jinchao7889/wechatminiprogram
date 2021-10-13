package com.orange.trip.service;

import com.orange.trip.domain.TripInventory;
import com.orange.trip.info.TripInventoryInfo;
import com.orange.trip.vo.TripInventoryVo;

import java.util.List;

public interface TripInventoryService {
    List<TripInventoryVo> addTripExpenses(List<TripInventoryInfo> tripInventoryInfos);
    List<TripInventory> getTripExpenses(Long tripId);

    List<TripInventoryVo> getAllTripInventoryVo(Long tripId);

    void delTripInventory(Integer id);
}

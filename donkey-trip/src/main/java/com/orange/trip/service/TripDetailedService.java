package com.orange.trip.service;

import com.orange.trip.domain.TripDetailed;
import com.orange.trip.info.TripDetailedInfo;

import java.util.List;

public interface TripDetailedService {

    TripDetailed addTripDetailed(TripDetailedInfo detailed);
    void delTripDetailed(Integer id);
    List<TripDetailed> getAllTripDetailed(Integer tripSummarizeId);
}

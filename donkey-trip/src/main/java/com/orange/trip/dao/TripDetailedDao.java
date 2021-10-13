package com.orange.trip.dao;

import com.orange.trip.domain.TripDetailed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripDetailedDao extends JpaRepository<TripDetailed,Integer> {
    List<TripDetailed> findAllByTripSummarizeId(Integer tripSummarizeId);
}

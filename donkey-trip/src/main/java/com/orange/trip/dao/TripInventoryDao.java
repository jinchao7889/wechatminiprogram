package com.orange.trip.dao;

import com.orange.trip.domain.TripInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripInventoryDao extends JpaRepository<TripInventory,Integer> {
    List<TripInventory> findAllByTripId(Long tripId);
}

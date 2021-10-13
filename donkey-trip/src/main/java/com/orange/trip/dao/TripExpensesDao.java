package com.orange.trip.dao;

import com.orange.trip.domain.TripExpenses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripExpensesDao extends JpaRepository<TripExpenses,Integer> {
    List<TripExpenses> findAllByTripId(Long tripId);
}

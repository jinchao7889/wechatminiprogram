package com.orange.trip.dao;

import com.orange.trip.domain.TripExpensesDetailed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripExpensesDetailedDao extends JpaRepository<TripExpensesDetailed,Integer> {
    List<TripExpensesDetailed> findAllByTripExpensesId(Integer tripExpensesId);
}

package com.orange.trip.dao;

import com.orange.trip.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripDao extends JpaRepository<Trip,Long>, JpaSpecificationExecutor<Trip> {
    Trip findByTravelsId(String travelsId);
}

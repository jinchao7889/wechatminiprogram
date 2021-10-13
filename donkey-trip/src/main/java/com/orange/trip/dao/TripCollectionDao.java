package com.orange.trip.dao;

import com.orange.trip.domain.TripCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripCollectionDao extends JpaRepository<TripCollection,Long> {
    TripCollection findByUserIdAndTripId(String userId,Long tripId);
    int deleteByUserIdAndTripId(String userId,Long tripId);

    Page<TripCollection> findAllByUserId(String userId, Pageable pageable);

    @Query("select count(*) from TripCollection t where t.tripId=:ti")
    Long findCount(@Param("ti") Long tripId);
}

package com.orange.trip.dao;

import com.orange.trip.domain.TripPool;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TripPoolDao extends MongoRepository<TripPool,Long> {
    TripPool findByTravelsId(String travelsId);
}

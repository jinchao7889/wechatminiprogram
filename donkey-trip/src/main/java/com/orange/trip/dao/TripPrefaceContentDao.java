package com.orange.trip.dao;

import com.orange.trip.domain.TripPrefaceContent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TripPrefaceContentDao extends MongoRepository<TripPrefaceContent,Long> {
}

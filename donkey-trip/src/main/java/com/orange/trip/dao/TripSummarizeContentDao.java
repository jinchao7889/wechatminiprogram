package com.orange.trip.dao;

import com.orange.trip.domain.TripSummarizeContent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TripSummarizeContentDao extends MongoRepository<TripSummarizeContent,String> {
}

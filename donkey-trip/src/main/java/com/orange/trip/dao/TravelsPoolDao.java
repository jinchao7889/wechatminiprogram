package com.orange.trip.dao;

import com.orange.trip.domain.TravelsPool;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TravelsPoolDao extends MongoRepository<TravelsPool,String> {

}

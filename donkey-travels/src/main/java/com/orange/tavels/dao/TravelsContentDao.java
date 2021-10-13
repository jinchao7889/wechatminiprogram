package com.orange.tavels.dao;

import com.orange.tavels.domain.TravelsContent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TravelsContentDao extends MongoRepository<TravelsContent,String> {

}

package com.orange.shop.dao;

import com.orange.shop.domain.ProductIntroduceContent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductIntroduceContentDao extends MongoRepository<ProductIntroduceContent,Long> {

}

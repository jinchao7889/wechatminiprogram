package com.orange.shop.dao;

import com.orange.shop.domain.ProductComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductCommentDao extends MongoRepository<ProductComment,String> {
    Page<ProductComment> findAllByEnableAndProductId(Boolean  enable,Long productId , Pageable pageable);
}

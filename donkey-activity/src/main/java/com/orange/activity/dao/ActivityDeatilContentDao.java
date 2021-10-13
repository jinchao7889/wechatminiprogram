package com.orange.activity.dao;

import com.orange.activity.domain.ActivityDeatilContent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityDeatilContentDao extends MongoRepository<ActivityDeatilContent,String> {
}

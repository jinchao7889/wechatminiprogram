package com.orange.activity.dao;

import com.orange.activity.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityDao extends JpaRepository<Activity,String>, JpaSpecificationExecutor<Activity> {
    @Modifying
    @Query("update Activity as a set a.activityStatus=:aa where a.id=:id")
    int updateStatus(@Param("id") String activityId,@Param("aa") Integer status);

    List<Activity> findAllByTitleLike(String title);
}

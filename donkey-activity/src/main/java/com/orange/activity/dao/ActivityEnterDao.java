package com.orange.activity.dao;

import com.orange.activity.domain.ActivityEnter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ActivityEnterDao extends JpaRepository<ActivityEnter,Long> , JpaSpecificationExecutor<ActivityEnter> {

    Page<ActivityEnter> findAllByUserId(String userId, Pageable pageable);

    Page<ActivityEnter> findAll( Pageable pageable);

    @Modifying
    @Query("update ActivityEnter as a set a.paymentStatus=:aa where a.orderId=:id")
    int updateStatus(@Param("id") String orderId, @Param("aa") Integer status);

    ActivityEnter findActivityEnterByOrderId(String orderId);

    List<ActivityEnter> findAllByActivityId(String activityId);


}

package com.orange.trip.dao;

import com.orange.trip.domain.TripSummarize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TripSummarizeDao extends JpaRepository<TripSummarize,Integer>, JpaSpecificationExecutor<TripSummarize> {

    /**
     * 数据的删除与恢复
     */
    @Modifying
    @Query("update TripSummarize t set t.enable=false where t.id=:id")
    void delById(@Param("id") Integer id);

    List<TripSummarize> findAllByTripId(Long tripId);
}

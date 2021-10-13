package com.orange.trip.dao;

import com.orange.trip.domain.Fabulous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FabulousDao extends JpaRepository<Fabulous,Integer> {
    Fabulous findFabulousByAnswerIdAndUserId(Integer answerId,String userId);

    @Modifying
    @Query("delete from  Fabulous f where f.answerId=:ai and f.userId=:ui")
    void delByAnswerIdAndUserId(@Param("ai") Integer answerId,@Param("ui") String userId);

    @Query("select count(*) from Fabulous f where f.answerId=:ai")
    Long getCountByAnswerId(@Param("ai") Integer answerId);
}

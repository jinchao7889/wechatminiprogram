package com.orange.trip.dao;

import com.orange.trip.domain.TripAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripAnswerDao extends JpaRepository<TripAnswer,Integer>, JpaSpecificationExecutor<TripAnswer> {
    @Query("select t.answerUserId from TripAnswer t where t.id=:id")
    String getUserIdById(@Param("id") Integer id);
}

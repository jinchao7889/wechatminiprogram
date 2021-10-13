package com.orange.trip.dao;

import com.orange.trip.domain.TripQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripQuestionsDao extends JpaRepository<TripQuestions,Long>, JpaSpecificationExecutor<TripQuestions> {
    @Modifying
    @Query("update TripQuestions t set t.enable=false where t.id=:id")
    void updateQuestion(@Param("id") Integer id);
}

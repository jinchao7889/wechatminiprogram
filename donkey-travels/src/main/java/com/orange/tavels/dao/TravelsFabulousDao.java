package com.orange.tavels.dao;

import com.orange.tavels.domain.TravelsFabulous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TravelsFabulousDao extends JpaRepository<TravelsFabulous,Integer> {
    TravelsFabulous findTravelsFabulousByCommentIdAndUserId(Integer commentId,String userId);

    void deleteByCommentIdAndUserId(Integer commentId,String userId);

    @Query("select count(*) from TravelsFabulous t where t.commentId = :ci")
    Long getCountByCommentId(@Param("ci") Integer ci);
}

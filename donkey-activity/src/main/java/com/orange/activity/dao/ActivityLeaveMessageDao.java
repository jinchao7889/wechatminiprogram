package com.orange.activity.dao;

import com.orange.activity.domain.ActivityLeaveMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ActivityLeaveMessageDao extends JpaRepository<ActivityLeaveMessage,Integer> , JpaSpecificationExecutor<ActivityLeaveMessage> {


    @Query("select count (*) from ActivityLeaveMessage t where t.activityId=:ai and t.enable=true ")
    Long findCountByActivityId(@Param("ai") String activityId);

    @Query("select count (*) from ActivityLeaveMessage t where t.parentId=:pi and t.enable=true")
    int findCountById(@Param("pi") Integer parentId);
}

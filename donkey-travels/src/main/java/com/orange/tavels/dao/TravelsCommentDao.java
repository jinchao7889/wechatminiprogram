package com.orange.tavels.dao;

import com.orange.tavels.domain.TravelsComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TravelsCommentDao extends JpaRepository<TravelsComment,Integer> , JpaSpecificationExecutor<TravelsComment> {
    @Query("select t.answerUserId from TravelsComment t where t.id=:id")
    String findAnswerUserIdById(@Param("id") String id);

    @Modifying
    @Query("update TravelsComment t set t.enable =false where t.id =:id")
    void delCommentById(@Param("id") Integer id);

    @Query("select count (*) from TravelsComment t where t.travelsId=:ti and t.enable=true ")
    Long findCountByTravelsId(@Param("ti") String travelsId);

    @Query("select count (*) from TravelsComment t where t.parentId=:pi and t.enable=true")
    int findCountById(@Param("pi") Integer parentId);


}

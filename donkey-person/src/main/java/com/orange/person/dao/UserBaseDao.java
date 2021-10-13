package com.orange.person.dao;

import com.orange.person.domain.UserBase;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface UserBaseDao extends JpaRepository<UserBase,String> {
    @Query("select u.nickname,u.portraitUrl from  UserBase as u where u.id =:id")
    Map<String,String> findUserBaseById(@Param("id") String id);

    UserBase findByUsername(String username);

    @Modifying
    @Query("update UserBase as u set u.portraitUrl =:urs where  u.id =:userId")
    int updateUserBase(@Param("userId") String userId,@Param("urs") String portraitUrl);
}

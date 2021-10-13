package com.orange.person.dao;

import com.orange.person.domain.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserConnectionDao extends JpaRepository<UserConnection,Integer> {
    @Query("select u.userId from UserConnection u where u.providerId=:pi and u.providerUserId=:pui")
    String  findUserIdByProviderIdAndProviderUserId(@Param("pi") String providerId,@Param("pui") String providerUserId);

}

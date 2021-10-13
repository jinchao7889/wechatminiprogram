package com.orange.person.dao;

import com.orange.person.domain.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserAuthenticationDao extends JpaRepository<UserAuthentication,String>, JpaSpecificationExecutor<UserAuthentication> {
    UserAuthentication findByUserIdAndAuditStatus(String userId,Integer status);
}

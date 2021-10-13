package com.orange.person.service;

import com.orange.person.domain.UserConnection;


public interface UserConnectionService {
    /**
     * 新增一个openId的连接
     * @param providerId
     * @param providerUserId
     * @param userId
     * @return
     */
    UserConnection addUserConnection(String providerId,String providerUserId,String userId);

    /**
     * 通过Id来获得UserId
     * @param providerId
     * @param providerUserId
     * @return
     */
    String getUserId(String providerId,String providerUserId);
}

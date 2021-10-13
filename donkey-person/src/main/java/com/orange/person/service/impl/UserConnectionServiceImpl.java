package com.orange.person.service.impl;

import com.orange.person.dao.UserConnectionDao;
import com.orange.person.domain.UserConnection;
import com.orange.person.service.UserConnectionService;
import com.orange.share.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserConnectionServiceImpl implements UserConnectionService {
    @Autowired
    UserConnectionDao userConnectionDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserConnection addUserConnection(String providerId, String providerUserId, String userId) {
        if (StringUtils.isBlank(providerId)||StringUtils.isBlank(providerUserId)||StringUtils.isBlank(userId)){
            throw  new RuntimeException("privide等为空，注册失败");
        }
        UserConnection userConnection = new UserConnection();
        userConnection.setUserId(userId);
        userConnection.setProviderId(providerId);
        userConnection.setProviderUserId(providerUserId);
        log.info("数据连接"+ JsonUtil.objectToString(userConnection));
        userConnectionDao.save(userConnection);
        return userConnection;
    }

    @Override
    public String getUserId(String providerId, String providerUserId) {
        if (StringUtils.isBlank(providerId)||StringUtils.isBlank(providerUserId)){
            throw  new RuntimeException("privide等为空，查询失败");
        }
        return userConnectionDao.findUserIdByProviderIdAndProviderUserId(providerId,providerUserId);
    }
}

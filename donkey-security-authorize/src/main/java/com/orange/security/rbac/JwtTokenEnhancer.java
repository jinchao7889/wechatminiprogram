package com.orange.security.rbac;


import com.orange.person.constant.AuditStatus;
import com.orange.person.constant.AuthenticationGrade;
import com.orange.person.dao.UserAuthenticationDao;
import com.orange.person.dao.UserBaseDao;
import com.orange.person.domain.UserAuthentication;
import com.orange.person.domain.UserBase;
import com.orange.person.info.UserInfo;
import com.orange.share.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JwtTokenEnhancer implements TokenEnhancer {
    @Autowired
    UserBaseDao userBaseDao;
    @Autowired
    UserAuthenticationDao authenticationDao;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String,Object> info=new HashMap<>();
        UserBase userBase;
        if (oAuth2Authentication.getPrincipal() instanceof UserBase){
             userBase= (UserBase) oAuth2Authentication.getPrincipal();
             log.info(JsonUtil.objectToString(userBase));
        }else {
            String userId= (String) oAuth2Authentication.getPrincipal();
             userBase= userBaseDao.findOne(userId);
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userBase,userInfo);
        UserAuthentication userAuthentication= authenticationDao.findByUserIdAndAuditStatus(userBase.getUserId(), AuditStatus.AUDIT_SUCCESS.getCode());
        if (userAuthentication!=null){
            userInfo.setAuthenticationGrade(userAuthentication.getAuthenticationGrade());
        }else {
            userInfo.setAuthenticationGrade(AuthenticationGrade.UNCERTIFIED.getCode());
        }

        info.put("user_info",userInfo);


        SecurityContextHolder.getContext().setAuthentication(oAuth2Authentication);
        ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(info);
        return oAuth2AccessToken;
    }
}

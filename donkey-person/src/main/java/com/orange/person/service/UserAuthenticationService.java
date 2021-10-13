package com.orange.person.service;

import com.orange.person.domain.UserAuthentication;
import com.orange.person.dto.AuthenticationDto;
import com.orange.person.info.AuditPageInfo;
import com.orange.share.vo.PageVo;

public interface UserAuthenticationService {
    UserAuthentication addUserAuthentication(UserAuthentication authentication);
    UserAuthentication getAuthentication();

    /**
     * 获得审核等级
     * @return
     */
    Integer getAuthenticationGrade(String userId);

    UserAuthentication upAuthentication(AuthenticationDto authenticationDto);

    PageVo getPage(AuditPageInfo auditPageInfo);

    UserAuthentication getByUserId(String userId);
}

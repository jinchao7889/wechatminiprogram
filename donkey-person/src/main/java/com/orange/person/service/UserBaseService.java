package com.orange.person.service;

import com.orange.person.domain.UserBase;
import com.orange.person.info.UserInfo;
import com.orange.share.page.PageInfo;
import com.orange.share.vo.PageVo;

public interface UserBaseService {
    UserBase addUserBase(UserBase userBase);
    UserBase getUserBase(String userId);
    PageVo getPage(PageInfo pageInfo);
    UserInfo updateP(String url);
}

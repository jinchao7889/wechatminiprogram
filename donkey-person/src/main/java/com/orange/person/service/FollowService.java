package com.orange.person.service;

import com.orange.person.domain.Follow;
import com.orange.share.page.PageInfo;
import com.orange.share.vo.PageVo;

public interface FollowService {
    /**
     * 新增关注
     * @param beUserId
     * @return
     */
    Follow addFollow(String beUserId);

    /**
     * 取消关注
     * @param beUserId
     */
    void cancelFollow(String beUserId);

    Boolean isFollow(String beUserId,String userId);

    PageVo getOwnerPage(PageInfo pageInfo);
}

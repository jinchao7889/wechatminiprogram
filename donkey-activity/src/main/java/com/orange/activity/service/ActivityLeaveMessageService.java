package com.orange.activity.service;

import com.orange.activity.info.ActivityLeaveMessageInfo;
import com.orange.activity.info.ActivityLeaveMessagePageInfo;
import com.orange.activity.vo.ActivityLeaveMessageVo;
import com.orange.share.vo.PageVo;

public interface ActivityLeaveMessageService {
    ActivityLeaveMessageVo addComment(ActivityLeaveMessageInfo commentInfo);

    PageVo getPageComment(ActivityLeaveMessagePageInfo pageInfo);

    PageVo getPageSecComment(ActivityLeaveMessagePageInfo pageInfo);

    void delComment(Integer commentId);

    Long getCountComment(String travelId);
}

package com.orange.activity.service;

import com.orange.activity.domain.ActivityEnter;
import com.orange.activity.dto.ActivityEnterDto;
import com.orange.activity.info.AcivityEnterPageInfo;
import com.orange.activity.vo.ActivityEnterDetailVo;
import com.orange.activity.vo.ActivityVo;
import com.orange.share.page.PageInfo;
import com.orange.share.vo.PageVo;

import java.util.List;
import java.util.Map;

public interface ActivityEnterService {
    Map addActivityEnter(ActivityEnterDto activityEnter, String spbill_create_ip);
    void activityPaySuccess(String orderId);
    void activityPayCancel(String orderId);
    PageVo getPage(PageInfo pageInfo);

    ActivityEnterDetailVo getActivityEnter(Long id);

    PageVo getMgPage(AcivityEnterPageInfo pageInfo);

    List<ActivityEnterDetailVo> getAll(String activityId);

    void cancelEnter(Long id);

}

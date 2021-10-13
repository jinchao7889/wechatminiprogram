package com.orange.activity.service;

import com.orange.activity.domain.Activity;
import com.orange.activity.dto.ActivityDto;
import com.orange.activity.info.ActivityPageInfo;
import com.orange.activity.vo.ActivityQueryVo;
import com.orange.activity.vo.ActivityVo;
import com.orange.share.vo.PageVo;

import java.util.List;

public interface ActivityService {
    ActivityDto addActivity(ActivityDto activityDto);

    PageVo getPageActivity(ActivityPageInfo pageInfo);

    PageVo getMgPageActivity(ActivityPageInfo pageInfo);

    void updateActivity(String activityId,Integer status);

    ActivityDto getActivity(String id);
    void delImg(Integer id);

    List<ActivityQueryVo> getActivityQuery(String act);

}

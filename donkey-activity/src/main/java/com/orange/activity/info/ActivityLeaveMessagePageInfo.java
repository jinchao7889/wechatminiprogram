package com.orange.activity.info;

import com.orange.share.page.PageInfo;
import lombok.Data;

@Data
public class ActivityLeaveMessagePageInfo extends PageInfo {
    String activityId;
    Integer parentId;

    /**
     * 评论次级ID
     * 被回答者的ID
     */
    Integer secondaryId;
}

package com.orange.activity.info;

import com.orange.share.page.PageInfo;
import lombok.Data;

@Data
public class AcivityEnterPageInfo extends PageInfo {
    String activityId;
    Integer payStatus;
    String realName;
}

package com.orange.trip.info;

import com.orange.share.page.PageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AnswerPageInfo extends PageInfo {
    Long questionId;
    Integer parentId;

    /**
     * 评论次级ID
     * 被回答者的ID
     */
    Integer secondaryId;
}

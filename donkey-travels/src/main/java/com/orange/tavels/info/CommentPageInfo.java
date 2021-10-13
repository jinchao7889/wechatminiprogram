package com.orange.tavels.info;


import com.orange.share.page.PageInfo;
import lombok.Data;

@Data
public class CommentPageInfo extends PageInfo {
    String travelsId;
    Integer parentId;

    /**
     * 评论次级ID
     * 被回答者的ID
     */
    Integer secondaryId;
}

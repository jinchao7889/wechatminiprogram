package com.orange.tavels.service;

import com.orange.share.vo.PageVo;
import com.orange.tavels.info.CommentInfo;
import com.orange.tavels.info.CommentPageInfo;
import com.orange.tavels.vo.CommentVo;

public interface TravelsCommentService {
    /**
     * 添加评论
     * @param commentInfo
     * @return
     */
    CommentVo addComment(CommentInfo commentInfo);

    PageVo getPageComment(CommentPageInfo pageInfo);

    PageVo getPageSecComment(CommentPageInfo pageInfo);

    void delComment(Integer commentId);

    Long getCountComment(String travelId);
}

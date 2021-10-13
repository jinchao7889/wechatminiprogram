package com.orange.tavels.service;


import com.orange.tavels.domain.TravelsFabulous;

public interface TravelsFabulousService {
    /**
     * 新增点赞
     * @param commentId
     * @return
     */
    TravelsFabulous addFabulous(Integer commentId);

    /**
     * 取消点赞
     * @param commentId
     */
    void cancelFabulous(Integer commentId);

    /**
     * 获得问题的点赞数量
     * @param commentId
     * @return
     */
    Long getCount(Integer commentId);

    /**
     * 用户是否点赞
     * @param commentId
     * @param userId
     * @return
     */
    Boolean isFabulous(Integer commentId,String userId);

}

package com.orange.trip.service;

import com.orange.trip.domain.Fabulous;

public interface FabulousService {
    /**
     * 新增点赞
     * @param answerId
     * @return
     */
    Fabulous addFabulous(Integer answerId);

    /**
     * 取消点赞
     * @param answerId
     */
    void cancelFabulous(Integer answerId);

    /**
     * 获得问题的点赞数量
     * @param answerId
     * @return
     */
    Long getCount(Integer answerId);

    /**
     * 用户是否点赞
     * @param answerId
     * @param userId
     * @return
     */
    Boolean isFabulous(Integer answerId,String userId);
}

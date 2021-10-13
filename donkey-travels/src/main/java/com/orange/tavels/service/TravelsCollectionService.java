package com.orange.tavels.service;

import com.orange.tavels.domain.TravelsCollection;

public interface TravelsCollectionService {
    /**
     * 收藏游记
     * @param travelsId
     * @return
     */
     TravelsCollection addTravelsCollection(String travelsId);

    /**
     * 取消收藏游记
     * @param travelsId
     * @return
     */
    void cancelTravelsCollection(String travelsId);

    Boolean isCollection(String userId,String travelsId);
    
    Long getCount(String travelsId);
}

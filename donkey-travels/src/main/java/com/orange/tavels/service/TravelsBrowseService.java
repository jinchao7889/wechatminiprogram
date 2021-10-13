package com.orange.tavels.service;

import com.orange.tavels.domain.TravelsBrowse;

public interface TravelsBrowseService {
    /**
     * 新增浏览
     */
    void addTravelsBrowse(String str);

    /**
     * 获得游记的浏览量
     * @param travelsId
     * @return
     */
    Long getCountByTravelsId(String travelsId);
}

package com.orange.tavels.service;

import com.orange.share.vo.PageVo;
import com.orange.tavels.domain.Travels;
import com.orange.tavels.dto.TravelContentDto;
import com.orange.tavels.info.TravelsInfo;
import com.orange.tavels.info.TravelsPageInfo;
import com.orange.tavels.vo.TravelContentUpVo;
import com.orange.tavels.vo.TravelsContentVo;

public interface TravelsService {
    Travels  addTravels(TravelsInfo travelsInfo);
    Travels findOne(String id);
    void delTravels(String id);
    PageVo getPage(TravelsPageInfo pageInfo);
    PageVo getMgPage(TravelsPageInfo pageInfo);
    PageVo getOwnerPage(TravelsPageInfo pageInfo);
    TravelsContentVo getTravelsContent(String travelsId);
    Travels getTravels(String travelsId);
    TravelContentUpVo upContent(TravelContentDto content);

    void release(String travelsId);
    void updateStatusTravels(String travelsId,Integer status);
}

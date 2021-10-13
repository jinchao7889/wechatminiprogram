package com.orange.trip.service;

import com.orange.share.page.PageInfo;
import com.orange.share.vo.PageVo;
import com.orange.tavels.vo.TravelsContentVo;
import com.orange.trip.domain.TravelsPool;
import com.orange.trip.domain.TripPool;
import com.orange.trip.vo.TripDetailsVo;

public interface TravelsPoolService {
    TravelsPool addTravelsPool(String travelsId);
    PageVo getPageOfTravels(PageInfo pageInfo);
    TripPool getTrip(Long tripId);
    PageVo getTripPage(PageInfo pageInfo);
    PageVo getOwnerTripPage(PageInfo pageInfo);
    TravelsContentVo getContent(String travelsId);
    TripDetailsVo getTripDetails(String travelsId);
}

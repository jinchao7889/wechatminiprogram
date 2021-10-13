package com.orange.trip.service;

import com.orange.share.page.PageInfo;
import com.orange.share.vo.PageVo;
import com.orange.tavels.info.TravelsPageInfo;
import com.orange.trip.domain.Trip;
import com.orange.trip.domain.TripPool;
import com.orange.trip.info.TripInfo;
import com.orange.trip.vo.TripDetailsVo;

public interface TripService {
    Trip addTrip(TripInfo trip);

    PageVo getTripByPage(PageInfo pageInfo);

    TripDetailsVo getTripDetails(String travelsId);

    Trip getTrip(String travelsId);

    PageVo getOwnerPage(TravelsPageInfo travelsPageInfo) ;

    TripPool getAllTripDetail(Long tripId);
}

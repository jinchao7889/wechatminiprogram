package com.orange.trip.service;

import com.orange.share.page.PageInfo;
import com.orange.share.vo.PageVo;
import com.orange.trip.domain.TripCollection;

public interface TripCollectionService {
    TripCollection addTripCollection(Long tripId);
    void cancelTripCollection(Long tripId);

    Boolean isCollection(String userId,Long tripId);

    PageVo getOwnerPage(PageInfo pageInfo);
    Long getCount(Long tripId);
}

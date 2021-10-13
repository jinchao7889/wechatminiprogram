package com.orange.trip.service.impl;

import com.orange.trip.dao.TripPrefaceContentDao;
import com.orange.trip.domain.TripPrefaceContent;
import com.orange.trip.service.TripPrefaceContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripPrefaceContentServiceImpl implements TripPrefaceContentService {
    @Autowired
    TripPrefaceContentDao tripPrefaceContentDao;


    @Override
    public TripPrefaceContent addTripPrefaceContent(TripPrefaceContent content) {
        tripPrefaceContentDao.save(content);
        return content;
    }

    @Override
    public TripPrefaceContent findTripPrefaceContent(Long id) {
        TripPrefaceContent content=tripPrefaceContentDao.findOne(id);

        return content;
    }
}

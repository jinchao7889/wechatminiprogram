package com.orange.trip.service.impl;

import com.orange.trip.dao.TripDetailedDao;
import com.orange.trip.domain.TripDetailed;
import com.orange.trip.info.TripDetailedInfo;
import com.orange.trip.service.TripDetailedService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TripDetailedServiceImpl  implements TripDetailedService {

    @Autowired
    TripDetailedDao tripDetailedDao;

    @Transactional
    @Override
    public TripDetailed addTripDetailed(TripDetailedInfo detailed) {

        TripDetailed tripDetailed = new TripDetailed();
        BeanUtils.copyProperties(detailed,tripDetailed);
        tripDetailedDao.save(tripDetailed);
        return tripDetailed;
    }

    @Override
    public void delTripDetailed(Integer id) {
        tripDetailedDao.delete(id);
    }

    @Override
    public List<TripDetailed> getAllTripDetailed(Integer tripSummarizeId) {
        return tripDetailedDao.findAllByTripSummarizeId(tripSummarizeId);
    }
}

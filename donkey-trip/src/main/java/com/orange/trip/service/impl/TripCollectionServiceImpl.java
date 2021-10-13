package com.orange.trip.service.impl;

import com.orange.person.dao.UserBaseDao;
import com.orange.person.domain.UserBase;
import com.orange.share.page.PageInfo;
import com.orange.share.page.SortUtil;
import com.orange.share.vo.PageVo;
import com.orange.tavels.dao.TravelsDao;
import com.orange.tavels.domain.Travels;
import com.orange.trip.dao.TravelsPoolDao;
import com.orange.trip.dao.TripCollectionDao;
import com.orange.trip.dao.TripDao;
import com.orange.trip.dao.TripPoolDao;
import com.orange.trip.domain.TravelsPool;
import com.orange.trip.domain.Trip;
import com.orange.trip.domain.TripCollection;
import com.orange.trip.domain.TripPool;
import com.orange.trip.service.TripCollectionService;
import com.orange.trip.vo.TripVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class TripCollectionServiceImpl implements TripCollectionService {
    @Autowired
    TripCollectionDao tripCollectionDao;
    @Autowired
    UserBaseDao userBaseDao;
    @Autowired
    TripDao tripDao;
    @Autowired
    TravelsDao travelsDao;
    @Autowired
    TravelsPoolDao travelsPoolDao;
    @Autowired
    TripPoolDao tripPoolDao;
    @Override
    public TripCollection addTripCollection(Long tripId) {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripCollection collection = tripCollectionDao.findByUserIdAndTripId(userId,tripId);
        if(collection!=null){
            throw new RuntimeException("用户已经收藏");
        }
        collection = new TripCollection();
        collection.setTripId(tripId);
        collection.setUserId(userId);
        tripCollectionDao.save(collection);
        return collection;
    }

    @Transactional
    @Override
    public void cancelTripCollection(Long tripId) {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripCollection collection = tripCollectionDao.findByUserIdAndTripId(userId,tripId);
        if(collection==null){
            throw new RuntimeException("用户没有收藏不能取消");
        }
        tripCollectionDao.deleteByUserIdAndTripId(userId,tripId);
    }

    @Override
    public Boolean isCollection(String userId, Long tripId) {
        TripCollection collection = tripCollectionDao.findByUserIdAndTripId(userId,tripId);
        if(collection!=null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public PageVo getOwnerPage(PageInfo pageInfo) {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<TripCollection> page = tripCollectionDao.findAllByUserId(userId, SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"createTime"));
        List<TripVo> tripVos = new ArrayList<>();
        for (TripCollection tripCollection:page.getContent()){
            TripPool trip = tripPoolDao.findOne(tripCollection.getTripId());
            TripVo vo = new TripVo();
            if (trip==null){
                continue;
            }
            TravelsPool travels= travelsPoolDao.findOne(trip.getTravelsId());

            UserBase userBase = userBaseDao.findOne(travels.getUserId());
            vo.setUserId(travels.getUserId());
            vo.setUserHead(userBase.getPortraitUrl());
            vo.setUserNickname(userBase.getNickname());
            vo.setUserGrade(userBase.getUserGrade());
            vo.setPerCapitaConsumption(travels.getPerCapitaConsumption());
            vo.setTripId(trip.getId());
            vo.setReleaseTime(travels.getReleaseTime());
            vo.setTravelDays(travels.getTravelDays());
            vo.setTravelType(travels.getTravelType());
            vo.setTitle(travels.getTitle());
            vo.setCoverMap(trip.getCoverMap());
            vo.setTravelsId(trip.getTravelsId());
            tripVos.add(vo);
        }

        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),tripVos);
    }

    @Override
    public Long getCount(Long tripId) {

        return tripCollectionDao.findCount(tripId);
    }
}

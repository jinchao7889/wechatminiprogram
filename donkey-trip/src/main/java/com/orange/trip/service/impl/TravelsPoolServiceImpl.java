package com.orange.trip.service.impl;

import com.orange.person.dao.UserBaseDao;
import com.orange.person.domain.UserBase;
import com.orange.person.service.FollowService;
import com.orange.share.page.PageInfo;
import com.orange.share.page.SortUtil;
import com.orange.share.util.JsonUtil;
import com.orange.share.vo.PageVo;
import com.orange.tavels.constant.TravelsStatus;
import com.orange.tavels.dao.TravelsContentDao;
import com.orange.tavels.dao.TravelsDao;
import com.orange.tavels.domain.Travels;
import com.orange.tavels.domain.TravelsBrowse;
import com.orange.tavels.domain.TravelsContent;
import com.orange.tavels.service.TravelsBrowseService;
import com.orange.tavels.service.TravelsCollectionService;
import com.orange.tavels.service.TravelsCommentService;
import com.orange.tavels.vo.TravelsContentVo;
import com.orange.tavels.vo.TravelsVo;
import com.orange.trip.dao.TravelsPoolDao;
import com.orange.trip.dao.TripDao;
import com.orange.trip.dao.TripPoolDao;
import com.orange.trip.dao.TripPrefaceContentDao;
import com.orange.trip.domain.*;
import com.orange.trip.service.TravelsPoolService;
import com.orange.trip.service.TripCollectionService;
import com.orange.trip.service.TripExpensesService;
import com.orange.trip.service.TripInventoryService;
import com.orange.trip.vo.TripDetailsVo;
import com.orange.trip.vo.TripVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TravelsPoolServiceImpl implements TravelsPoolService {
    @Autowired
    TravelsPoolDao travelsPoolDao;
    @Autowired
    TravelsDao travelsDao;
    @Autowired
    TravelsContentDao travelsContentDao;
    @Autowired
    TripPoolDao tripPoolDao;
    @Autowired
    TripDao tripDao;
    @Autowired
    TripInventoryService inventoryService;
    @Autowired
    TripExpensesService expensesService;
    @Autowired
    TripSummarizeServiceImpl summarizeService;
    @Autowired
    TripPrefaceContentDao prefaceContentDao;
    @Autowired
    UserBaseDao userBaseDao;
    @Autowired
    TravelsCommentService travelsCommentService;
    @Autowired
    TravelsBrowseService browseService;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    AmqpTemplate amqpTemplate;
    @Autowired
    TravelsCollectionService collectionService;
    @Autowired
    FollowService followService;
    @Autowired
    TripCollectionService tripCollectionService;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public TravelsPool addTravelsPool(String travelsId) {
        Travels travels = travelsDao.findOne(travelsId);
        if (travels==null){
            throw new RuntimeException("游记不存在");
        }
        TravelsPool travelsPool = new TravelsPool();
        BeanUtils.copyProperties(travels,travelsPool);
        travelsPool.setPerCapitaConsumption(travels.getPerCapitaConsumption().doubleValue());
        TravelsContent travelsContent = travelsContentDao.findOne(travelsId);
        if (travelsContent==null){
            travelsPool.setTravelContent("");

        }else {
            travelsPool.setTravelContent(travelsContent.getContent());

        }

        TripPool tripPool = new TripPool();
        Trip trip = tripDao.findByTravelsId(travelsId);
        tripPool.setId(trip.getId());
        travelsPool.setTripId(trip.getId());
        travelsPool.setTripCoverMap(trip.getCoverMap());
        travelsPoolDao.save(travelsPool);
        tripPool.setCoverMap(trip.getCoverMap());
        tripPool.setTripInventoryVos(inventoryService.getAllTripInventoryVo(trip.getId()));
        tripPool.setTripExpensesToVo(expensesService.getAll(trip.getId()));
        tripPool.setTripSummarizeVos(summarizeService.getALL(trip.getId()));
        TripPrefaceContent prefaceContent = prefaceContentDao.findOne(trip.getId());
        if (prefaceContent==null){
            tripPool.setTripPrefaceContent("");

        }else {
            tripPool.setTripPrefaceContent(prefaceContent.getContent());

        }
        tripPool.setTravelsId(travelsId);
        tripPoolDao.save(tripPool);
        travelsDao.updateStatus(travelsId, TravelsStatus.EXAMINE_SUCESS.getCode());
        return travelsPool;
    }

    @Override
    public PageVo getPageOfTravels(PageInfo pageInfo) {
        Page<TravelsPool> page;
        switch (pageInfo.getCondition()){
            case 1:
                page=travelsPoolDao.findAll(SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"releaseTime"));
                break;
            case 2:
                page= travelsPoolDao.findAll(SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.ASC,"travelDays"));
                break;
            case 3:
                page= travelsPoolDao.findAll(SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.ASC,"perCapitaConsumption"));
                break;
            default:throw new RuntimeException("不存在的条件");
        }
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),coverTravelsVo(page.getContent()));
    }

    @Override
    public TripPool getTrip(Long tripId) {


        return tripPoolDao.findOne(tripId);
    }

    @Override
    public PageVo getTripPage(PageInfo pageInfo) {
        Page<TravelsPool> page;
        switch (pageInfo.getCondition()){
            case 1:
                page=travelsPoolDao.findAll(SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"releaseTime"));
                break;
            case 2:
                page= travelsPoolDao.findAll(SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.ASC,"travelDays"));
                break;
            case 3:
                page= travelsPoolDao.findAll(SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.ASC,"perCapitaConsumption"));
                break;
            default:throw new RuntimeException("不存在的条件");
        }
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),coverToTrip(page.getContent()));
    }

    private List<TripVo> coverToTrip(List<TravelsPool> travelsPools ){
        List<TripVo> tripVos = new ArrayList<>();
        for (TravelsPool travels:travelsPools){
            TripVo vo = new TripVo();
            UserBase userBase = userBaseDao.findOne(travels.getUserId());
            Trip trip = tripDao.findByTravelsId(travels.getId());

            vo.setTravelsId(travels.getId());
            vo.setCoverMap(travels.getTripCoverMap());
            vo.setTravelType(travels.getTravelType());
            vo.setUserGrade(userBase.getUserGrade());
            vo.setUserHead(userBase.getPortraitUrl());
            vo.setCollectionVolume(tripCollectionService.getCount(trip.getId()));
            vo.setPerCapitaConsumption(travels.getPerCapitaConsumption());
            vo.setReleaseTime(travels.getReleaseTime());
            vo.setTitle(travels.getTitle());
            vo.setTravelDays(travels.getTravelDays());
            vo.setUserNickname(userBase.getNickname());
            tripVos.add(vo);
        }
        return tripVos;
    }

    @Override
    public PageVo getOwnerTripPage(PageInfo pageInfo) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        TravelsPool travelsPool = new TravelsPool();
        travelsPool.setUserId(userId);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("collectionVolume","browseVolume"
                ,"commentVolume","fabulousVolume","travelDays","enable"

        );
        Example<TravelsPool> example = Example.of(travelsPool,matcher);
        Page<TravelsPool> page=travelsPoolDao.findAll(example,SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"releaseTime"));

        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),coverToTrip(page.getContent()));
    }

    @Override
    public TravelsContentVo getContent(String travelsId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TravelsPool travels= travelsPoolDao.findOne(travelsId);
        if (travels==null){
            throw new RuntimeException("游记找不到咯");
        }
        TravelsBrowse browse = new TravelsBrowse();
        browse.setUserId(userId);
        browse.setTravelsId(travelsId);
        amqpTemplate.convertAndSend("add_browse", JsonUtil.objectToString(browse));

        UserBase userBase = userBaseDao.findOne(travels.getUserId());
        TravelsContentVo contentVo = new TravelsContentVo();
        BeanUtils.copyProperties(travels,contentVo);
        contentVo.setHead(userBase.getPortraitUrl());
        contentVo.setUserId(travels.getUserId());
        contentVo.setTravelsId(travels.getId());
        contentVo.setNickname(userBase.getNickname());
        contentVo.setTravelDays(travels.getTravelDays());
        contentVo.setTravelType(travels.getTravelType());
        contentVo.setFabulousVolume(collectionService.getCount(travelsId));
        contentVo.setIsFabulous(collectionService.isCollection(userId,travelsId));
        contentVo.setIsFollow(followService.isFollow(travels.getUserId(),userId));
        contentVo.setPerCapitaConsumption(travels.getPerCapitaConsumption());
        contentVo.setIsOwner(userId.equals(travels.getUserId()));
        contentVo.setContent(travels.getTravelContent());
        return contentVo;
    }

    @Override
    public TripDetailsVo getTripDetails(String travelsId) {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TravelsPool travels = travelsPoolDao.findOne(travelsId);
        TripDetailsVo detailsVo = new TripDetailsVo();
        BeanUtils.copyProperties(travels,detailsVo);
        UserBase userBase = userBaseDao.findOne(travels.getUserId());
        detailsVo.setCollectionVolume(tripCollectionService.getCount(travels.getTripId()));
        detailsVo.setUserHead(userBase.getPortraitUrl());
        detailsVo.setUserNickname(userBase.getNickname());
        detailsVo.setUserGrade(userBase.getUserGrade());
        detailsVo.setIsFollow(followService.isFollow(travels.getUserId(),userId));
        detailsVo.setTripId(travels.getTripId());
        detailsVo.setTravelsId(travels.getId());
        detailsVo.setIsCollection(tripCollectionService.isCollection(userId,travels.getTripId()));
        detailsVo.setTravelDays(travels.getTravelDays());
        detailsVo.setTravelType(travels.getTravelType());
        detailsVo.setTitle(travels.getTitle());
        detailsVo.setIsOwner(userId.equals(travels.getUserId()));
        return detailsVo;
    }

    private List<TravelsVo> coverTravelsVo(List<TravelsPool> travelsList){
        List<TravelsVo> travelsVos = new ArrayList<>();
        for (TravelsPool travels:travelsList){
            TravelsVo travelsVo = new TravelsVo();
            BeanUtils.copyProperties(travels,travelsVo);
            UserBase userBase = userBaseDao.findOne(travels.getUserId());
            travelsVo.setHead(userBase.getPortraitUrl());
            travelsVo.setTravelsId(travels.getId());
            travelsVo.setNickname(userBase.getNickname());
            travelsVo.setFabulousVolume(travels.getCollectionVolume());
            travelsVo.setCommentVolume(travelsCommentService.getCountComment(travels.getId()).intValue());
            travelsVo.setBrowseVolume(browseService.getCountByTravelsId(travels.getId()));
            travelsVos.add(travelsVo);
        }
        return travelsVos;
    }

}

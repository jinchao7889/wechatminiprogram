package com.orange.trip.service.impl;

import com.orange.person.dao.UserBaseDao;
import com.orange.person.domain.UserBase;
import com.orange.person.service.FollowService;
import com.orange.share.page.PageInfo;
import com.orange.share.page.SortUtil;
import com.orange.share.vo.PageVo;
import com.orange.tavels.constant.TravelsStatus;
import com.orange.tavels.dao.TravelsDao;
import com.orange.tavels.domain.Travels;
import com.orange.tavels.info.TravelsPageInfo;
import com.orange.tavels.service.impl.TravelsServiceImpl;
import com.orange.trip.dao.TripDao;
import com.orange.trip.dao.TripPrefaceContentDao;
import com.orange.trip.domain.Trip;
import com.orange.trip.domain.TripPool;
import com.orange.trip.domain.TripPrefaceContent;
import com.orange.trip.info.TripInfo;
import com.orange.trip.service.TripCollectionService;
import com.orange.trip.service.TripExpensesService;
import com.orange.trip.service.TripInventoryService;
import com.orange.trip.service.TripService;
import com.orange.trip.vo.TripDetailsVo;
import com.orange.trip.vo.TripVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TripServiceImpl implements TripService {
    @Autowired
    TripDao tripDao;
    @Autowired
    UserBaseDao userBaseDao;
    @Autowired
    TripCollectionService collectionService;
    @Autowired
    FollowService followService;
    @Autowired
    TravelsDao travelsDao;
    @Autowired
    TripInventoryService inventoryService;
    @Autowired
    TripExpensesService expensesService;
    @Autowired
    TripSummarizeServiceImpl summarizeService;
    @Autowired
    TripPrefaceContentDao prefaceContentDao;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Trip addTrip(TripInfo tripInfo) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("userId:"+ userId);
        Trip trip = tripDao.findByTravelsId(tripInfo.getTravelsId());
        if (trip!=null){
            BeanUtils.copyProperties(tripInfo,trip);
            tripDao.saveAndFlush(trip);
        }else {
            trip=new Trip();
            trip.setCreateTime(System.currentTimeMillis()/1000);
            trip.setUserId(userId);
            trip.setCoverMap(tripInfo.getCoverMap());
            trip.setTravelsId(tripInfo.getTravelsId());
            tripDao.saveAndFlush(trip);
        }
        return trip;
    }



    @Override
    public PageVo getTripByPage(PageInfo pageInfo) {
        Page<Travels> page;
        switch (pageInfo.getCondition()){
            case 1:
                page= travelsDao.findAll(TravelsServiceImpl.TravelsSpec.method1(TravelsStatus.EXAMINE_SUCESS.getCode()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"releaseTime"));
                break;
            case 2:
                page= travelsDao.findAll(TravelsServiceImpl.TravelsSpec.method1(TravelsStatus.EXAMINE_SUCESS.getCode()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.ASC,"perCapitaConsumption"));
                break;
            case 3:
                page= travelsDao.findAll(TravelsServiceImpl.TravelsSpec.method1(TravelsStatus.EXAMINE_SUCESS.getCode()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.ASC,"travelDays"));

                break;
            default:throw new RuntimeException("不存在的条件");
        }
        List<TripVo> tripVos = new ArrayList<>();
        for (Travels travels:page.getContent()){
            TripVo vo = new TripVo();
            UserBase userBase = userBaseDao.findOne(travels.getUserId());
            Trip trip = tripDao.findByTravelsId(travels.getId());
            vo.setTravelsId(travels.getId());
            vo.setCoverMap(travels.getCoverMap());
            vo.setTravelType(travels.getTravelType());
            vo.setUserGrade(userBase.getUserGrade());
            vo.setUserHead(userBase.getPortraitUrl());
            vo.setCollectionVolume(collectionService.getCount(trip.getId()));
            vo.setPerCapitaConsumption(travels.getPerCapitaConsumption().doubleValue());
            vo.setReleaseTime(travels.getReleaseTime());
            vo.setTitle(travels.getTitle());
            vo.setTravelDays(travels.getTravelDays());
            vo.setUserNickname(userBase.getNickname());
            tripVos.add(vo);
        }
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),tripVos);
    }



    @Override
    public TripDetailsVo getTripDetails(String travelsId) {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Trip trip = tripDao.findByTravelsId(travelsId);
        TripDetailsVo detailsVo = new TripDetailsVo();
        BeanUtils.copyProperties(trip,detailsVo);
        UserBase userBase = userBaseDao.findOne(trip.getUserId());
        Travels travels= travelsDao.findOne(trip.getTravelsId());
        detailsVo.setCollectionVolume(collectionService.getCount(trip.getId()));
        detailsVo.setUserHead(userBase.getPortraitUrl());
        detailsVo.setUserNickname(userBase.getNickname());
        detailsVo.setUserGrade(userBase.getUserGrade());
        detailsVo.setIsFollow(followService.isFollow(trip.getUserId(),userId));
        detailsVo.setTripId(trip.getId());
        detailsVo.setTravelsId(trip.getTravelsId());
        detailsVo.setIsCollection(collectionService.isCollection(userId,trip.getId()));
        detailsVo.setTravelDays(travels.getTravelDays());
        detailsVo.setTravelType(travels.getTravelType());
        detailsVo.setTitle(travels.getTitle());
        return detailsVo;
    }

    @Override
    public Trip getTrip(String travelsId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Trip trip = tripDao.findByTravelsId(travelsId);
        if (trip==null){
            trip = new Trip();
            trip.setCreateTime(System.currentTimeMillis()/1000);
            trip.setUserId(userId);
            trip.setTravelsId(travelsId);
            tripDao.save(trip);
        }
        return trip;
    }

    @Override
    public PageVo getOwnerPage(TravelsPageInfo pageInfo) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Page<Trip> page= tripDao.findAll(TripSpec.method2(userId), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"createTime"));
        List<TripVo> tripVos = new ArrayList<>();
        for (Trip trip:page.getContent()){
            TripVo vo = new TripVo();
            UserBase userBase = userBaseDao.findOne(trip.getUserId());
            vo.setUserId(trip.getUserId());
            vo.setUserHead(userBase.getPortraitUrl());
            vo.setUserNickname(userBase.getNickname());
            vo.setUserGrade(userBase.getUserGrade());
            vo.setCollectionVolume(trip.getCollectionVolume().longValue());
            Travels travels= travelsDao.findOne(trip.getTravelsId());
            if (travels==null){
                continue;
            }
            vo.setPerCapitaConsumption(travels.getPerCapitaConsumption().doubleValue());
            vo.setTripId(trip.getId());
            vo.setReleaseTime(trip.getCreateTime());
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
    public TripPool getAllTripDetail(Long tripId) {
        TripPool tripPool = new TripPool();
        tripPool.setTripInventoryVos(inventoryService.getAllTripInventoryVo(tripId));
        tripPool.setTripExpensesToVo(expensesService.getAll(tripId));
        tripPool.setTripSummarizeVos(summarizeService.getALL(tripId));
        TripPrefaceContent prefaceContent = prefaceContentDao.findOne(tripId);
        if (prefaceContent==null){
            tripPool.setTripPrefaceContent("");

        }else {
            tripPool.setTripPrefaceContent(prefaceContent.getContent());

        }
        return tripPool;
    }


    public static class TripSpec {
        public static Specification<Trip> method1(Integer st) {
            return new Specification<Trip>() {
                @Override
                public Predicate toPredicate(Root<Trip> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Integer> status = root.get("status");
                    Predicate predicate;
                    predicate = criteriaBuilder.and(criteriaBuilder.equal(status, st));
                    return predicate;
                }
            };
        }
        public static Specification<Trip> method2(String ui) {
            return new Specification<Trip>() {
                @Override
                public Predicate toPredicate(Root<Trip> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<String> userId = root.get("userId");
                    Predicate predicate;

                        predicate = criteriaBuilder.and(criteriaBuilder.equal(userId, ui));

                    return predicate;
                }
            };
        }
    }
}

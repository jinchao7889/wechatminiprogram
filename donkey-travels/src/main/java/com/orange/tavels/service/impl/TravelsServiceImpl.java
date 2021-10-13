package com.orange.tavels.service.impl;

import com.orange.person.dao.UserBaseDao;
import com.orange.person.domain.UserBase;
import com.orange.person.service.FollowService;
import com.orange.share.page.SortUtil;
import com.orange.share.util.JsonUtil;
import com.orange.share.vo.PageVo;
import com.orange.tavels.constant.TravelsStatus;
import com.orange.tavels.dao.TravelsContentDao;
import com.orange.tavels.dao.TravelsDao;
import com.orange.tavels.domain.Travels;
import com.orange.tavels.domain.TravelsBrowse;
import com.orange.tavels.domain.TravelsContent;
import com.orange.tavels.dto.TravelContentDto;
import com.orange.tavels.info.TravelsInfo;
import com.orange.tavels.info.TravelsPageInfo;
import com.orange.tavels.service.*;
import com.orange.tavels.vo.TravelContentUpVo;
import com.orange.tavels.vo.TravelsContentVo;
import com.orange.tavels.vo.TravelsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
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

@Slf4j
@Service
public class TravelsServiceImpl implements TravelsService {

    @Autowired
    TravelsDao travelsDao;
    @Autowired
    UserBaseDao userBaseDao;
    @Autowired
    TravelsContentDao travelsContentDao;
    @Autowired
    AmqpTemplate amqpTemplate;
    @Autowired
    TravelsBrowseService browseService;
    @Autowired
    TravelsCollectionService collectionService;
    @Autowired
    TravelsCommentService travelsCommentService;
    @Autowired
    FollowService followService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Travels addTravels(TravelsInfo travelsInfo) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Travels travels = new Travels();
        travels.setId(travelsInfo.getId());
        travels.setTitle(travelsInfo.getTitle());
        travels.setCoverMap(travelsInfo.getCoverMap());
        travels.setDepartureTime(travelsInfo.getDepartureTime());
        travels.setTravelType(travelsInfo.getTravelType());
        travels.setTravelDays(travelsInfo.getTravelDays());
        travels.setPerCapitaConsumption(travelsInfo.getPerCapitaConsumption());
        travels.setTravelDestination(travelsInfo.getTravelDestination());
        travels.setUserId(userId);
        travels.setTravelsStatus(TravelsStatus.EDITOR.getCode());
        travels.setCreateTime(System.currentTimeMillis()/1000);
        travels.setTravelsStatus(travelsInfo.getTravelsStatus());
        if (travelsInfo.getTravelsStatus()== TravelsStatus.EDITOR.getCode()){
            travels.setReleaseTime(System.currentTimeMillis()/1000);
        }
        travelsDao.saveAndFlush(travels);

        return travels;
    }

    @Override
    public Travels findOne(String id) {
        return travelsDao.findOne(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delTravels(String id) {
        travelsDao.delEnable(id);
    }

    @Override
    public PageVo getPage(TravelsPageInfo pageInfo) {
        Page<Travels> page;
        switch (pageInfo.getCondition()){
            case 1:
                page= travelsDao.findAll(TravelsSpec.method1(TravelsStatus.EXAMINE_SUCESS.getCode()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"releaseTime"));
                break;
            case 2:
            page= travelsDao.findAll(TravelsSpec.method1(TravelsStatus.EXAMINE_SUCESS.getCode()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.ASC,"perCapitaConsumption"));
                break;
            case 3:
                page= travelsDao.findAll(TravelsSpec.method1(TravelsStatus.EXAMINE_SUCESS.getCode()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.ASC,"travelDays"));

                break;
            default:throw new RuntimeException("不存在的条件");
        }

        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),coverTravelsVo(page.getContent()));
    }

    @Override
    public PageVo getMgPage(TravelsPageInfo pageInfo) {
        Page<Travels> page;
        switch (pageInfo.getCondition()){
            case 1:
                page= travelsDao.findAll(TravelsSpec.method1(pageInfo.getStatus()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"releaseTime"));
                break;
            case 2:
                page= travelsDao.findAll(TravelsSpec.method1(pageInfo.getStatus()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.ASC,"perCapitaConsumption"));
                break;
            case 3:
                page= travelsDao.findAll(TravelsSpec.method1(pageInfo.getStatus()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.ASC,"travelDays"));
                break;
            default:throw new RuntimeException("不存在的条件");
        }
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),coverTravelsVo(page.getContent()));
    }

    private List<TravelsVo> coverTravelsVo(List<Travels> travelsList){
        List<TravelsVo> travelsVos = new ArrayList<>();
        for (Travels travels:travelsList){
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

    @Override
    public PageVo getOwnerPage(TravelsPageInfo pageInfo) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("获得自己的游记");
        Page<Travels> page= travelsDao.findAll(TravelsSpec.method2(pageInfo.getStatus(),userId), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"createTime"));
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),coverTravelsVo(page.getContent()));
    }

    @Override
    public TravelsContentVo getTravelsContent(String travelsId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Travels travels= travelsDao.findOne(travelsId);
        if (travels==null){
            throw new RuntimeException("游记找不到咯");
        }

        UserBase userBase = userBaseDao.findOne(travels.getUserId());
        TravelsContentVo contentVo = new TravelsContentVo();
        BeanUtils.copyProperties(travels,contentVo);
        TravelsContent travelsContent =travelsContentDao.findOne(travelsId);
        contentVo.setHead(userBase.getPortraitUrl());
        contentVo.setUserId(travels.getUserId());
        contentVo.setTravelsId(travels.getId());
        contentVo.setNickname(userBase.getNickname());
        contentVo.setTravelDays(travels.getTravelDays());
        contentVo.setTravelType(travels.getTravelType());

        contentVo.setPerCapitaConsumption(travels.getPerCapitaConsumption().doubleValue());
        if (travelsContent==null){
            contentVo.setContent("");

        }else {
            contentVo.setContent(travelsContent.getContent());
        }
        return contentVo;
    }

    @Override
    public Travels getTravels(String travelsId) {
        return travelsDao.findOne(travelsId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TravelContentUpVo upContent(TravelContentDto content) {
        Travels travels = travelsDao.findOne(content.getTravelsId());

        TravelContentUpVo travelContentUpVo = new TravelContentUpVo();
        travelContentUpVo.setTitle(travels.getTitle());
        TravelsContent travelsContent = new TravelsContent();
        travelsContent.setId(content.getTravelsId());
        travelsContent.setContent(content.getContent());

        travelsContentDao.save(travelsContent);
        travelContentUpVo.setTravelsId(content.getTravelsId());
        travelContentUpVo.setContent(content.getContent());
        return travelContentUpVo;
    }

    @Transactional(rollbackFor =Exception.class )
    @Override
    public void release(String travelsId) {
        travelsDao.updateStatus(travelsId,TravelsStatus.RELEASE.getCode());
    }
    @Transactional(rollbackFor =Exception.class )
    @Override
    public void updateStatusTravels(String travelsId, Integer status) {
        travelsDao.updateStatus(travelsId,status);

    }


    public static class TravelsSpec {
        public static Specification<Travels> method1(Integer status) {
            return new Specification<Travels>() {
                @Override
                public Predicate toPredicate(Root<Travels> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("enable");
                    Path<Integer> travelsStatus = root.get("travelsStatus");
                    Predicate predicate;
                    if (status==0){
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true));

                    }else {
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(travelsStatus, status));
                    }
                    return predicate;
                }
            };
        }
        public static Specification<Travels> method2(Integer status,String userId) {
            return new Specification<Travels>() {
                @Override
                public Predicate toPredicate(Root<Travels> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("enable");
                    Path<Integer> travelsStatus = root.get("travelsStatus");
                    Path<String> ui = root.get("userId");
                    Predicate predicate;
                    if (status==0){
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(ui, userId));

                    }else if (status==2){
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(ui, userId),criteriaBuilder.notEqual(travelsStatus,TravelsStatus.EDITOR.getCode()));
                    }
                    else {
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(travelsStatus, status),criteriaBuilder.equal(ui, userId));
                    }
                    return predicate;
                }
            };
        }
    }
}

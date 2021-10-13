package com.orange.trip.service.impl;

import com.orange.share.page.SortUtil;
import com.orange.share.vo.PageVo;
import com.orange.trip.dao.TripDetailedDao;
import com.orange.trip.dao.TripSummarizeContentDao;
import com.orange.trip.dao.TripSummarizeDao;
import com.orange.trip.domain.TripDetailed;
import com.orange.trip.domain.TripSummarize;
import com.orange.trip.domain.TripSummarizeContent;
import com.orange.trip.dto.TripSummarizeDto;
import com.orange.trip.info.SummarizePageInfo;
import com.orange.trip.info.TripSummarizeInfo;
import com.orange.trip.service.TripSummarizeService;
import com.orange.trip.vo.TripSummarizeVo;
import lombok.extern.slf4j.Slf4j;
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
public class TripSummarizeServiceImpl implements TripSummarizeService {

    @Autowired
    TripSummarizeDao tripSummarizeDao;
    @Autowired
    TripDetailedDao tripDetailedDao;
    @Autowired
    TripSummarizeContentDao contentDao;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public TripSummarize addTripSummarize(TripSummarizeInfo summarizeInfo) {
        TripSummarize tripSummarize = new TripSummarize();
        BeanUtils.copyProperties(summarizeInfo,tripSummarize);
        TripSummarizeContent content = new TripSummarizeContent();
        content.setContent(tripSummarize.getCorrectionsContent());
        contentDao.save(content);
        tripSummarize.setCorrectionsContent(content.getId());
        tripSummarizeDao.saveAndFlush(tripSummarize);
        tripSummarize.setCorrectionsContent(content.getContent());
        return tripSummarize;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<TripSummarizeVo> addManyTripSummarize(List<TripSummarizeDto> summarizeDtos) {
        List<TripSummarizeVo> list = new ArrayList<>();
        for (TripSummarizeDto summarizeDto:summarizeDtos){
            TripSummarize tripSummarize = new TripSummarize();
            BeanUtils.copyProperties(summarizeDto,tripSummarize);
            TripSummarizeContent content = new TripSummarizeContent();
            content.setContent(tripSummarize.getCorrectionsContent());
            contentDao.save(content);
            tripSummarize.setCorrectionsContent(content.getId());
            tripSummarizeDao.saveAndFlush(tripSummarize);
            TripSummarizeVo vo = new TripSummarizeVo();

            BeanUtils.copyProperties(tripSummarize,vo);
            vo.setCorrectionsContent(content.getContent());
            List<TripDetailed> detaileds = new ArrayList<>();
            for (TripDetailed detailed :summarizeDto.getTripDetail()){
                detailed.setTripSummarizeId(tripSummarize.getId());
                tripDetailedDao.saveAndFlush(detailed);
                detaileds.add(detailed);
            }
            vo.setTripDetail(detaileds);
            list.add(vo);
        }
        return list;
    }


    @Transactional
    @Override
    public void delTripSummarize(Integer id) {
        tripSummarizeDao.delete(id);
    }

    @Override
    public PageVo getTripSummarizeByPage(SummarizePageInfo pageInfo) {

        Page<TripSummarize> page = tripSummarizeDao.findAll(SummarizeSpec.method1(pageInfo.getTripId()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.ASC,"serialNumber"));
        List<TripSummarizeVo> list = new ArrayList<>();
        for (TripSummarize tripSummarize:page.getContent()){
            TripSummarizeVo vo = new TripSummarizeVo();
            BeanUtils.copyProperties(tripSummarize,vo);
            TripSummarizeContent summarizeContent = contentDao.findOne(tripSummarize.getCorrectionsContent());
            if (summarizeContent!=null){
                vo.setCorrectionsContent(summarizeContent.getContent());

            }else {
                vo.setCorrectionsContent("");
            }
            List<TripDetailed> tripDetaileds = tripDetailedDao.findAllByTripSummarizeId(tripSummarize.getId());
            vo.setTripDetail(tripDetaileds);
            list.add(vo);
        }
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),list);

    }

    @Override
    public List<TripSummarizeVo> getALL(Long tripId) {
        List<TripSummarize> tripSummarizes = tripSummarizeDao.findAllByTripId(tripId);
        List<TripSummarizeVo> list = new ArrayList<>();
        for (TripSummarize tripSummarize:tripSummarizes){
            TripSummarizeVo vo = new TripSummarizeVo();
            BeanUtils.copyProperties(tripSummarize,vo);
            List<TripDetailed> tripDetaileds = tripDetailedDao.findAllByTripSummarizeId(tripSummarize.getId());
            vo.setTripDetail(tripDetaileds);
            TripSummarizeContent tripSummarizeContent = contentDao.findOne(tripSummarize.getCorrectionsContent());
            if (tripSummarizeContent==null){
                vo.setCorrectionsContent("");

            }else {
                vo.setCorrectionsContent(tripSummarizeContent.getContent());

            }
            list.add(vo);
        }
        return list;
    }

    public static class SummarizeSpec {
        public static Specification<TripSummarize> method1(Long tripId) {
            return new Specification<TripSummarize>() {
                @Override
                public Predicate toPredicate(Root<TripSummarize> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("enable");
                    Path<Integer> ti = root.get("tripId");
                    Predicate predicate;
                    predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(ti,tripId));
                    return predicate;
                }
            };
        }
    }
}

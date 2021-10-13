package com.orange.trip.service.impl;

import com.orange.person.dao.UserBaseDao;
import com.orange.person.domain.UserBase;
import com.orange.share.page.SortUtil;
import com.orange.share.vo.PageVo;
import com.orange.trip.constant.QuestionsStatus;
import com.orange.trip.dao.TripQuestionsDao;
import com.orange.trip.domain.TripQuestions;
import com.orange.trip.info.TripQuestionOwnerPageInfo;
import com.orange.trip.info.TripQuestionPageInfo;
import com.orange.trip.info.TripQuestionsInfo;
import com.orange.trip.service.TripQuestionsService;
import com.orange.trip.vo.TripQuestionsVo;
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
public class TripQuestionsServiceImpl implements TripQuestionsService {

    @Autowired
    UserBaseDao userBaseDao;

    @Autowired
    TripQuestionsDao tripQuestionsDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TripQuestionsVo addQuestion(TripQuestionsInfo info) {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripQuestions tripQuestions = new TripQuestions();
        tripQuestions.setTripId(info.getTripId());
        tripQuestions.setQuestion(info.getQuestion());
        tripQuestions.setCreateTime(System.currentTimeMillis()/1000);
        tripQuestions.setQuestionUserId(userId);
        tripQuestions.setSolveStatus(QuestionsStatus.RESOLVED.getCode());
        tripQuestionsDao.save(tripQuestions);
        return coverQuestion(tripQuestions);
    }

    @Override
    public PageVo getQuestion(TripQuestionPageInfo pageInfo) {
        Page<TripQuestions> page= tripQuestionsDao.findAll(TripSpec.method1(pageInfo.getTripId(),pageInfo.getQuestionStatus()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"createTime"));
        List<TripQuestionsVo> questionsVos = new ArrayList<>();
        for (TripQuestions questions :page.getContent()){
            questionsVos.add(coverQuestion(questions));
        }
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),questionsVos);
    }

    @Override
    public PageVo getOwnerQuestion(TripQuestionPageInfo pageInfo) {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<TripQuestions> page= tripQuestionsDao.findAll(TripSpec.method2(pageInfo.getTripId(),pageInfo.getQuestionStatus(),userId), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"createTime"));
        List<TripQuestionsVo> questionsVos = new ArrayList<>();
        for (TripQuestions questions :page.getContent()){
            questionsVos.add(coverQuestion(questions));
        }
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),questionsVos);
    }

    public TripQuestionsVo coverQuestion(TripQuestions questions){
        TripQuestionsVo vo = new TripQuestionsVo();
        BeanUtils.copyProperties(questions,vo);
        UserBase userBase = userBaseDao.findOne(questions.getQuestionUserId());
        vo.setUserHead(userBase.getPortraitUrl());
        vo.setUserNickname(userBase.getNickname());
        return vo;
    }

    @Transactional
    @Override
    public void delQuestion(Integer questionsId) {
        tripQuestionsDao.updateQuestion(questionsId);
    }

    @Transactional
    @Override
    public void questionReplyNumberPlus(Long questionsId) {
        TripQuestions questions= tripQuestionsDao.findOne(questionsId);
        if (questions==null){
            return;
        }
        questions.setReplyNumber(questions.getReplyNumber()+1);
        tripQuestionsDao.save(questions);
    }

    @Transactional
    @Override
    public void questionReplyNumberReduce(Long questionsId) {
        TripQuestions questions= tripQuestionsDao.findOne(questionsId);
        if (questions==null){
            return;
        }
        questions.setReplyNumber(questions.getReplyNumber()-1);
        tripQuestionsDao.save(questions);
    }

    public static class TripSpec {
        public static Specification<TripQuestions> method1(Integer tripId,Integer questionStatus) {
            return new Specification<TripQuestions>() {
                @Override
                public Predicate toPredicate(Root<TripQuestions> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("enable");
                    Path<Integer> ti = root.get("tripId");
                    Path<Integer> qs = root.get("solveStatus");
                    Predicate predicate;
                    if (questionStatus==QuestionsStatus.ALL.getCode()){
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(ti,tripId));

                    }else {
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(ti,tripId),criteriaBuilder.equal(qs,questionStatus));

                    }
                    return predicate;
                }
            };
        }

        public static Specification<TripQuestions> method2(Integer tripId,Integer questionStatus,String userId) {
            return new Specification<TripQuestions>() {
                @Override
                public Predicate toPredicate(Root<TripQuestions> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("enable");
                    Path<Integer> ti = root.get("tripId");
                    Path<Integer> qs = root.get("solveStatus");
                    Path<String> qui = root.get("questionUserId");
                    Predicate predicate;
                    if (questionStatus==QuestionsStatus.ALL.getCode()){
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(ti,tripId),criteriaBuilder.equal(qui,userId));

                    }else {
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(ti,tripId),criteriaBuilder.equal(qs,questionStatus),criteriaBuilder.equal(qui,userId));

                    }
                    return predicate;
                }
            };
        }
    }
}

package com.orange.trip.service.impl;

import com.orange.person.dao.UserBaseDao;
import com.orange.person.domain.UserBase;
import com.orange.share.page.SortUtil;
import com.orange.share.vo.PageVo;
import com.orange.trip.dao.TripAnswerDao;
import com.orange.trip.dao.TripQuestionsDao;
import com.orange.trip.domain.TripAnswer;
import com.orange.trip.info.AnswerInfo;
import com.orange.trip.info.AnswerPageInfo;
import com.orange.trip.service.FabulousService;
import com.orange.trip.service.TripAnswerService;
import com.orange.trip.service.TripQuestionsService;
import com.orange.trip.vo.TripAnswerVo;
import com.orange.trip.vo.TripQuestionAnswerVo;
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
public class TripAnswerServiceImpl implements TripAnswerService {

    @Autowired
    TripAnswerDao tripAnswerDao;
    @Autowired
    UserBaseDao userBaseDao;
    @Autowired
    TripQuestionsService questionsService;
    @Autowired
    FabulousService fabulousService;
    @Autowired
    TripQuestionsDao tripQuestionsDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TripAnswerVo addAnswer(AnswerInfo answerInfo) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TripAnswer answer = new TripAnswer();
        BeanUtils.copyProperties(answerInfo,answer);
        answer.setAnswerUserId(userId);
        answer.setCreateTime(System.currentTimeMillis()/1000);
        answer.setQuestionId(answerInfo.getQuestionId());
        tripAnswerDao.save(answer);
        /**
         * 问题回复数量加1
         */
        questionsService.questionReplyNumberPlus(answerInfo.getQuestionId());
        if (answerInfo.getParentId()!=-1){
            answerNumberPlus(answerInfo.getParentId());
        }
        return answerVoConvert(answer,userId);
    }

    @Override
    public PageVo getAnswerPage(AnswerPageInfo answerPageInfo) {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         Page<TripAnswer> page = tripAnswerDao.findAll(AnswerSpec.method1(answerPageInfo.getQuestionId(),answerPageInfo.getParentId()),
                 SortUtil.buildDESC(answerPageInfo.getPage(),answerPageInfo.getSize(), Sort.Direction.DESC,"createTime"));
         List<TripAnswerVo> vos = new ArrayList<>();
         for (TripAnswer answer : page.getContent()) {
             vos.add(answerVoConvert(answer,userId));
         }

        TripQuestionsVo questionsVo= questionsService.coverQuestion(tripQuestionsDao.findOne(answerPageInfo.getQuestionId()));
        TripQuestionAnswerVo answerVo = new TripQuestionAnswerVo();
        BeanUtils.copyProperties(questionsVo,answerVo);
        answerVo.setTripAnswers(vos);
        return new PageVo(page.getTotalPages(),answerPageInfo.getPage(),page.getTotalElements(),answerVo);
    }

    public static class AnswerSpec {
        public static Specification<TripAnswer> method1(Long questionId,Integer parentId) {
            return new Specification<TripAnswer>() {
                @Override
                public Predicate toPredicate(Root<TripAnswer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("enable");
                    Path<Long> qi = root.get("questionId");
                    Path<Integer> pi = root.get("parentId");
                    Predicate predicate;
                    predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(pi,parentId),criteriaBuilder.equal(qi,questionId));
                    return predicate;
                }
            };
        }
    }

    private TripAnswerVo answerVoConvert(TripAnswer answer,String look_userId){
        TripAnswerVo answerVo = new TripAnswerVo();
        BeanUtils.copyProperties(answer,answerVo);
        UserBase userBase = userBaseDao.findOne(answer.getAnswerUserId());
        answerVo.setAnswerHead(userBase.getPortraitUrl());
        answerVo.setAnswerNickname(userBase.getNickname());
        if (answer.getParentId()!=-1){
             String  userId= tripAnswerDao.getUserIdById(answer.getParentId());
            UserBase toUserBase = userBaseDao.findOne(userId);
            answerVo.setToAnswerHead(toUserBase.getPortraitUrl());
            answerVo.setToAnswerNickname(toUserBase.getNickname());
        }
        answerVo.setIsFabulous(fabulousService.isFabulous(answer.getId(),look_userId));
        return  answerVo;
    }

    /**
     * 问题的回复数量+1
     */
    private void answerNumberPlus(Integer id){
        TripAnswer tripAnswer = tripAnswerDao.findOne(id);
        if (tripAnswer==null){
            return;
        }
        tripAnswer.setReplyNumber(tripAnswer.getReplyNumber()+1);
        tripAnswerDao.save(tripAnswer);
    }

    /**
     * 问题的回复数量-1
     */
    private void answerNumberRedus(Integer id){
        TripAnswer tripAnswer = tripAnswerDao.findOne(id);
        if (tripAnswer==null){
            return;
        }
        tripAnswer.setReplyNumber(tripAnswer.getReplyNumber()-1);
        tripAnswerDao.save(tripAnswer);
    }
}

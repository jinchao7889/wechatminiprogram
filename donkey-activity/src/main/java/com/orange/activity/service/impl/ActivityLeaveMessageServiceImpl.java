package com.orange.activity.service.impl;

import com.orange.activity.dao.ActivityLeaveMessageDao;
import com.orange.activity.domain.ActivityLeaveMessage;
import com.orange.activity.info.ActivityLeaveMessageInfo;
import com.orange.activity.info.ActivityLeaveMessagePageInfo;
import com.orange.activity.service.ActivityLeaveMessageService;
import com.orange.activity.vo.ActivityLeaveMessageVo;
import com.orange.person.dao.UserBaseDao;
import com.orange.person.domain.UserBase;
import com.orange.share.page.SortUtil;
import com.orange.share.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityLeaveMessageServiceImpl implements ActivityLeaveMessageService {
    @Autowired
    ActivityLeaveMessageDao messageDao;
    @Autowired
    UserBaseDao userBaseDao;
    @Override
    public ActivityLeaveMessageVo addComment(ActivityLeaveMessageInfo commentInfo) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ActivityLeaveMessage message = new ActivityLeaveMessage();
        message.setAnswerUserId(userId);
        message.setCreateTime(System.currentTimeMillis()/1000);
        message.setActivityId(commentInfo.getActivityId());
        message.setContent(commentInfo.getContent());
        message.setParentId(commentInfo.getParentId());
        message.setSecondaryId(commentInfo.getSecondaryId());
        message.setToAnswerUserId(commentInfo.getToAnswerUserId());
        messageDao.save(message);
        return answerVoConvert(message,userId);
    }

    @Override
    public PageVo getPageComment(ActivityLeaveMessagePageInfo pageInfo) {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Page<ActivityLeaveMessage> page= messageDao.findAll(ActivityLeaveMessageSpec.method1(pageInfo.getActivityId(),pageInfo.getParentId(),pageInfo.getSecondaryId()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.ASC,"createTime"));
        List<ActivityLeaveMessageVo> vos = new ArrayList<>();
        for (ActivityLeaveMessage comment :page.getContent()){
            ActivityLeaveMessageVo vo=answerVoConvert(comment,userId);
            if (pageInfo.getParentId()==-1)
            {
                List<ActivityLeaveMessageVo> vos2 = new ArrayList<>();
                Page<ActivityLeaveMessage> page1= messageDao.findAll(ActivityLeaveMessageSpec.method2(pageInfo.getActivityId(),comment.getId()), SortUtil.buildDESC(0,2, Sort.Direction.ASC,"createTime"));
                for (ActivityLeaveMessage comment1:page1.getContent()){
                    ActivityLeaveMessageVo vo2=answerVoConvert(comment1,userId
                    );
                    vos2.add(vo2);
                }
                vo.setSecComments(vos2);
            }
            vos.add(vo);
        }
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),vos);
    }

    @Override
    public PageVo getPageSecComment(ActivityLeaveMessagePageInfo pageInfo) {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ActivityLeaveMessage travelsComment= messageDao.findOne(pageInfo.getParentId());
        ActivityLeaveMessageVo v=answerVoConvert(travelsComment,userId);
        Page<ActivityLeaveMessage> page= messageDao.findAll(ActivityLeaveMessageSpec.method2(pageInfo.getActivityId(),pageInfo.getParentId()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.ASC,"createTime"));
        List<ActivityLeaveMessageVo> vos = new ArrayList<>();
        for (ActivityLeaveMessage comment :page.getContent()){
            ActivityLeaveMessageVo vo=answerVoConvert(comment,userId);

            vos.add(vo);
        }
        v.setSecComments(vos);
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),v);
    }

    @Override
    public void delComment(Integer commentId) {
//        ActivityLeaveMessage travelsComment= messageDao.findOne(commentId);
//        if (travelsComment==null){
//            throw new RuntimeException("此评论已经删除");
//        }
//        travelsDao.updateCommentVolumeReduce(travelsComment.getTravelsId());
//        commentDao.delCommentById(commentId);
    }

    @Override
    public Long getCountComment(String travelId) {
        return messageDao.findCountByActivityId(travelId);
    }
    private ActivityLeaveMessageVo answerVoConvert(ActivityLeaveMessage comment,String userId) {
        ActivityLeaveMessageVo vo = new ActivityLeaveMessageVo();
        BeanUtils.copyProperties(comment,vo);
        UserBase userBase = userBaseDao.findOne(comment.getAnswerUserId());
        vo.setAnswerHead(userBase.getPortraitUrl());
        vo.setAnswerNickname(userBase.getNickname());
        vo.setReplyNumber(messageDao.findCountById(comment.getId()));
        if (comment.getSecondaryId()!=-1){
            UserBase toUserBase = userBaseDao.findOne(comment.getToAnswerUserId());

            vo.setToAnswerHead(toUserBase.getPortraitUrl());
            vo.setToAnswerNickname(toUserBase.getNickname());
        }
        return vo;
    }

    public static class  ActivityLeaveMessageSpec {
        public static Specification<ActivityLeaveMessage> method1(String ai, Integer pi, Integer si) {
            return new Specification<ActivityLeaveMessage>() {
                @Override
                public Predicate toPredicate(Root<ActivityLeaveMessage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("enable");
                    Path<String> activityId = root.get("activityId");
                    Path<Integer> parentId = root.get("parentId");
                    Path<Integer> secondaryId = root.get("secondaryId");
                    Predicate predicate;
                    if (pi==-1){
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(activityId, ai),criteriaBuilder.equal(parentId, pi));
                    }else {
                        if (si==-1){
                            predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(activityId, ai),criteriaBuilder.equal(activityId, ai),criteriaBuilder.equal(parentId, pi));

                        }else {
                            predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(activityId, ai),criteriaBuilder.equal(activityId, ai),criteriaBuilder.equal(secondaryId, si));

                        }
                    }
                    return predicate;
                }
            };
        }
        public static Specification<ActivityLeaveMessage> method2(String ai,Integer pi) {
            return new Specification<ActivityLeaveMessage>() {
                @Override
                public Predicate toPredicate(Root<ActivityLeaveMessage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("enable");
                    Path<String> activityId = root.get("activityId");
                    Path<Integer> parentId = root.get("parentId");
                    Predicate predicate;
                    predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(activityId, ai),criteriaBuilder.equal(parentId, pi));

                    return predicate;
                }
            };
        }
    }
}

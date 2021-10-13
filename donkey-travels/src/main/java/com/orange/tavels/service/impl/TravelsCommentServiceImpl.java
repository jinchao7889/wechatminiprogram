package com.orange.tavels.service.impl;

import com.orange.person.dao.UserBaseDao;
import com.orange.person.domain.UserBase;
import com.orange.share.page.SortUtil;
import com.orange.share.vo.PageVo;
import com.orange.tavels.dao.TravelsCommentDao;
import com.orange.tavels.dao.TravelsDao;
import com.orange.tavels.domain.TravelsComment;
import com.orange.tavels.info.CommentInfo;
import com.orange.tavels.info.CommentPageInfo;
import com.orange.tavels.service.TravelsCommentService;
import com.orange.tavels.service.TravelsFabulousService;
import com.orange.tavels.vo.CommentVo;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
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
public class
TravelsCommentServiceImpl implements TravelsCommentService {
    @Autowired
    TravelsCommentDao commentDao;
    @Autowired
    UserBaseDao userBaseDao;
    @Autowired
    TravelsFabulousService fabulousService;
    @Autowired
    TravelsDao travelsDao;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommentVo addComment(CommentInfo commentInfo) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TravelsComment comment = new TravelsComment();
        comment.setAnswerUserId(userId);
        comment.setCreateTime(System.currentTimeMillis()/1000);
        comment.setTravelsId(commentInfo.getTravelsId());
        comment.setContent(commentInfo.getContent());
        comment.setParentId(commentInfo.getParentId());
        comment.setSecondaryId(commentInfo.getSecondaryId());
        comment.setToAnswerUserId(commentInfo.getToAnswerUserId());
        commentDao.save(comment);
        travelsDao.updateCommentVolumePush(commentInfo.getTravelsId());

        return answerVoConvert(comment,userId);
    }

    @Override
    public PageVo getPageComment(CommentPageInfo pageInfo) {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Page<TravelsComment> page= commentDao.findAll(CommentSpec.method1(pageInfo.getTravelsId(),pageInfo.getParentId(),pageInfo.getSecondaryId()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.ASC,"createTime"));
        List<CommentVo> vos = new ArrayList<>();
        for (TravelsComment comment :page.getContent()){
            CommentVo vo=answerVoConvert(comment,userId);
            if (pageInfo.getParentId()==-1)
            {
                List<CommentVo> vos2 = new ArrayList<>();
                Page<TravelsComment> page1= commentDao.findAll(CommentSpec.method2(pageInfo.getTravelsId(),comment.getId()), SortUtil.buildDESC(0,2, Sort.Direction.ASC,"createTime"));
                for (TravelsComment comment1:page1.getContent()){
                    CommentVo vo2=answerVoConvert(comment1,userId);
                    vos2.add(vo2);
                }
                vo.setSecComments(vos2);
            }
            vos.add(vo);
        }
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),vos);
    }

    @Override
    public PageVo getPageSecComment(CommentPageInfo pageInfo) {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TravelsComment travelsComment= commentDao.findOne(pageInfo.getParentId());
        CommentVo v=answerVoConvert(travelsComment,userId);
        Page<TravelsComment> page= commentDao.findAll(CommentSpec.method2(pageInfo.getTravelsId(),pageInfo.getParentId()), SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.ASC,"createTime"));
        List<CommentVo> vos = new ArrayList<>();
        for (TravelsComment comment :page.getContent()){
            CommentVo vo=answerVoConvert(comment,userId);

            vos.add(vo);
        }
        v.setSecComments(vos);
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),v);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delComment(Integer commentId) {
        TravelsComment travelsComment= commentDao.findOne(commentId);
        if (travelsComment==null){
            throw new RuntimeException("此评论已经删除");
        }
        travelsDao.updateCommentVolumeReduce(travelsComment.getTravelsId());
        commentDao.delCommentById(commentId);
    }

    @Override
    public Long getCountComment(String travelId) {
        return commentDao.findCountByTravelsId(travelId);
    }

    private CommentVo answerVoConvert(TravelsComment comment,String userId) {
        CommentVo vo = new CommentVo();
        BeanUtils.copyProperties(comment,vo);
        UserBase userBase = userBaseDao.findOne(comment.getAnswerUserId());
        vo.setAnswerHead(userBase.getPortraitUrl());
        vo.setAnswerNickname(userBase.getNickname());
        vo.setLikeNumber(fabulousService.getCount(comment.getId()).intValue());
        vo.setReplyNumber(commentDao.findCountById(comment.getId()));
        if (comment.getSecondaryId()!=-1){
            UserBase toUserBase = userBaseDao.findOne(comment.getToAnswerUserId());

            vo.setToAnswerHead(toUserBase.getPortraitUrl());
            vo.setToAnswerNickname(toUserBase.getNickname());
        }
        vo.setIsFabulous(fabulousService.isFabulous(comment.getId(),userId));
        return vo;
    }

    public static class CommentSpec {
        public static Specification<TravelsComment> method1(String ti,Integer pi,Integer si) {
            return new Specification<TravelsComment>() {
                @Override
                public Predicate toPredicate(Root<TravelsComment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("enable");
                    Path<String> travelsId = root.get("travelsId");
                    Path<Integer> parentId = root.get("parentId");
                    Path<Integer> secondaryId = root.get("secondaryId");
                    Predicate predicate;
                    if (pi==-1){
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(travelsId, ti),criteriaBuilder.equal(parentId, pi));
                    }else {
                        if (si==-1){
                            predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(travelsId, ti),criteriaBuilder.equal(travelsId, ti),criteriaBuilder.equal(parentId, pi));

                        }else {
                            predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(travelsId, ti),criteriaBuilder.equal(travelsId, ti),criteriaBuilder.equal(secondaryId, si));

                        }
                    }
                    return predicate;
                }
            };
        }
        public static Specification<TravelsComment> method2(String ti,Integer pi) {
            return new Specification<TravelsComment>() {
                @Override
                public Predicate toPredicate(Root<TravelsComment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("enable");
                    Path<String> travelsId = root.get("travelsId");
                    Path<Integer> parentId = root.get("parentId");
                    Predicate predicate;
                    predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(travelsId, ti),criteriaBuilder.equal(parentId, pi));

                    return predicate;
                }
            };
        }
    }
}

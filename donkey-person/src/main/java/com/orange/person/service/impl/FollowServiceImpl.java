package com.orange.person.service.impl;

import com.orange.person.dao.FollowDao;
import com.orange.person.dao.UserBaseDao;
import com.orange.person.domain.Follow;
import com.orange.person.domain.UserBase;
import com.orange.person.service.FollowService;
import com.orange.person.vo.FollowVo;
import com.orange.share.page.PageInfo;
import com.orange.share.page.SortUtil;
import com.orange.share.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowDao followDao;
    @Autowired
    UserBaseDao userBaseDao;

    @Override
    public Follow addFollow(String beUserId) {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Follow follow = followDao.findByBeConcernedAndConcern(beUserId,userId);
        if (follow!=null){
            throw new RuntimeException("此用户已经关注");
        }
         follow = new Follow();
        follow.setCreateTime(System.currentTimeMillis()/1000);
        follow.setConcern(userId);
        follow.setBeConcerned(beUserId);
        followDao.save(follow);
        return follow;
    }

    @Transactional
    @Override
    public void cancelFollow(String beUserId) {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        followDao.deleteByBeConcernedAndConcern(beUserId,userId);
    }

    @Override
    public Boolean isFollow(String beUserId, String userId) {
        Follow follow = followDao.findByBeConcernedAndConcern(beUserId,userId);
        if (follow!=null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public PageVo getOwnerPage(PageInfo pageInfo) {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       Page<Follow> page = followDao.findAllByConcern(userId, SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"createTime"));
        List<FollowVo> userBaseVos = new ArrayList<>();
       for (Follow follow:page.getContent()) {
           FollowVo userBaseVo = new FollowVo();

           UserBase userBase =userBaseDao.findOne(follow.getBeConcerned());
           userBaseVo.setId(follow.getId());
           userBaseVo.setBeConcernedUserId(userBase.getId());
           userBaseVo.setNickname(userBase.getNickname());
           userBaseVo.setPortraitUrl(userBase.getPortraitUrl());
           userBaseVos.add(userBaseVo);
       }
       return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),userBaseVos);
    }
}

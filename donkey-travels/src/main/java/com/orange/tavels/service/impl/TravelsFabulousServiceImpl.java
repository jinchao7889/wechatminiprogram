package com.orange.tavels.service.impl;

import com.orange.tavels.dao.TravelsFabulousDao;
import com.orange.tavels.domain.TravelsFabulous;
import com.orange.tavels.service.TravelsFabulousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TravelsFabulousServiceImpl implements TravelsFabulousService {
    @Autowired
    TravelsFabulousDao fabulousDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TravelsFabulous addFabulous(Integer commentId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TravelsFabulous fabulous= fabulousDao.findTravelsFabulousByCommentIdAndUserId(commentId,userId);
        if (fabulous!=null){
            throw new RuntimeException("该用户已经点赞，不能重复点赞");
        }
        fabulous = new TravelsFabulous();
        fabulous.setCommentId(commentId);
        fabulous.setUserId(userId);
        fabulousDao.save(fabulous);
        return fabulous;
    }

    @Transactional
    @Override
    public void cancelFabulous(Integer commentId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        fabulousDao.deleteByCommentIdAndUserId(commentId,userId);
    }

    @Transactional
    @Override
    public Long getCount(Integer commentId) {
        return fabulousDao.getCountByCommentId(commentId);
    }

    @Override
    public Boolean isFabulous(Integer commentId, String userId) {
        TravelsFabulous fabulous= fabulousDao.findTravelsFabulousByCommentIdAndUserId(commentId,userId);
        if (fabulous!=null){
           return true;
        }else {
            return false;
        }
    }

}

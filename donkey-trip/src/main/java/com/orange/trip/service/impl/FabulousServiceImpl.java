package com.orange.trip.service.impl;

import com.orange.trip.dao.FabulousDao;
import com.orange.trip.domain.Fabulous;
import com.orange.trip.service.FabulousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FabulousServiceImpl implements FabulousService {

    @Autowired
    FabulousDao fabulousDao;

    @Transactional
    @Override
    public Fabulous addFabulous(Integer answerId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Fabulous fabulous= fabulousDao.findFabulousByAnswerIdAndUserId(answerId,userId);
        if (fabulous!=null){
            throw new RuntimeException("该用户已经点赞，不能重复点赞");
        }
        fabulous = new Fabulous();
        fabulous.setAnswerId(answerId);
        fabulous.setUserId(userId);
        fabulousDao.save(fabulous);

        return fabulous;
    }

    @Transactional
    @Override
    public void cancelFabulous(Integer answerId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        fabulousDao.delByAnswerIdAndUserId(answerId,userId);
    }

    @Override
    public Long getCount(Integer answerId) {
        return fabulousDao.getCountByAnswerId(answerId);
    }

    @Override
    public Boolean isFabulous(Integer answerId, String userId) {
        Fabulous fabulous= fabulousDao.findFabulousByAnswerIdAndUserId(answerId,userId);
        if (fabulous==null){
            return false;
        } else {
            return true;
        }
    }
}

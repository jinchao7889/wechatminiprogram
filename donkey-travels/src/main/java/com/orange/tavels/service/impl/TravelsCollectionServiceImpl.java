package com.orange.tavels.service.impl;

import com.orange.tavels.dao.TravelsCollectionDao;
import com.orange.tavels.dao.TravelsDao;
import com.orange.tavels.domain.TravelsCollection;
import com.orange.tavels.service.TravelsCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TravelsCollectionServiceImpl implements TravelsCollectionService {
    @Autowired
    TravelsCollectionDao travelsCollectionDao;
    @Autowired
    TravelsDao travelsDao;

    @Transactional
    @Override
    public TravelsCollection addTravelsCollection(String travelsId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TravelsCollection travelsCollection = travelsCollectionDao.findTravelsCollectionByUserIdAndTravelsId(userId,travelsId);
        if (travelsCollection==null){
            travelsCollection = new TravelsCollection();
            travelsCollection.setUserId(userId);
            travelsCollection.setCreateTime(System.currentTimeMillis()/1000);
            travelsCollection.setTravelsId(travelsId);
            travelsCollection.setEnable(false);
        }
        if(travelsCollection.getEnable()==null){
            travelsCollection.setEnable(false);
        }
        travelsCollection.setEnable(!travelsCollection.getEnable());
        travelsCollectionDao.saveAndFlush(travelsCollection);

        if (travelsCollection.getEnable()){
            travelsDao.updateCollectionVolumePush(travelsId);

        }else {
            travelsDao.updateCollectionVolumeReduce(travelsId);
        }


        return travelsCollection;
    }

    @Transactional
    @Override
    public void cancelTravelsCollection(String travelsId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        travelsCollectionDao.deleteByTravelsIdAndUserId(travelsId,userId);
        travelsDao.updateCollectionVolumeReduce(travelsId);

    }

    @Override
    public Boolean isCollection(String userId, String travelsId) {
        if (travelsCollectionDao.findTravelsCollectionByUserIdAndTravelsIdAndEnable(userId,travelsId,true)!=null){

            return true;
        }else {
            return false;

        }
    }

    @Override
    public Long getCount(String travelsId) {
        return travelsCollectionDao.findCountByTravelsId(travelsId);
    }


}

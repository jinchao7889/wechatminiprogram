package com.orange.tavels.service.impl;

import com.google.gson.Gson;
import com.orange.share.util.JsonUtil;
import com.orange.tavels.dao.TravelsBrowseDao;
import com.orange.tavels.dao.TravelsDao;
import com.orange.tavels.domain.TravelsBrowse;
import com.orange.tavels.service.TravelsBrowseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TravelsBrowseServiceImpl  implements TravelsBrowseService {
    @Autowired
    TravelsBrowseDao browseDao;
    @Autowired
    TravelsDao travelsDao;
    @Transactional
    @Override
    @RabbitListener(queues="add_browse")
    public void addTravelsBrowse(String str) {
        try {
            Gson gson = new Gson();
            TravelsBrowse browse=gson.fromJson(str,TravelsBrowse.class);
            if (StringUtils.isBlank(browse.getTravelsId())||StringUtils.isBlank(browse.getUserId())){
                log.error("新增浏览时 发送了空的数据");
                return;
            }
//            if (browseDao.findByUserIdAndTravelsId(browse.getUserId(),browse.getTravelsId())!=null){
//                return;
//            }
            browse.setCreateTime(System.currentTimeMillis()/1000);
            browseDao.save(browse);
            travelsDao.updateBrowseVolumePush(browse.getTravelsId());

        }catch (Exception e){
            log.error("新增浏览量时出错",e);
        }
    }

    @Override
    public Long getCountByTravelsId(String travelsId) {
        return browseDao.getCountByTravelsId(travelsId);
    }
}

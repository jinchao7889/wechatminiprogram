package com.orange.person.service.impl;

import com.orange.person.dao.UserBaseDao;
import com.orange.person.domain.UserBase;
import com.orange.person.info.UserInfo;
import com.orange.person.service.UserBaseService;
import com.orange.person.vo.UserBaseVo;
import com.orange.share.page.PageInfo;
import com.orange.share.page.SortUtil;
import com.orange.share.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserBaseServiceImpl implements UserBaseService {
    @Autowired
    UserBaseDao userBaseDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserBase addUserBase(UserBase userBase) {
        userBase.setCreateTime(System.currentTimeMillis()/1000);
        userBase.setLastLoginTime(System.currentTimeMillis()/1000);
        userBaseDao.save(userBase);
        return userBase;
    }

    @Override
    public UserBase getUserBase(String userId) {
        return userBaseDao.findOne(userId);
    }

    @Override
    public PageVo getPage(PageInfo pageInfo) {
        Page<UserBase> page = userBaseDao.findAll(SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"createTime"));
        List<UserBaseVo> userBaseVos = new ArrayList<>();
        for (UserBase userBase :page.getContent()){
            UserBaseVo userBaseVo = new UserBaseVo();
            BeanUtils.copyProperties(userBase,userBaseVo);
            userBaseVos.add(userBaseVo);
        }

        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),userBaseVos);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserInfo updateP(String url) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserBase userBase = userBaseDao.findOne(userId);
        userBase.setPortraitUrl(url);
        userBaseDao.saveAndFlush(userBase);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userBase,userInfo);
        return userInfo;
    }
}

package com.orange.person.service.impl;

import com.orange.person.constant.AuditStatus;
import com.orange.person.constant.AuthenticationGrade;
import com.orange.person.dao.UserAuthenticationDao;
import com.orange.person.dao.UserBaseDao;
import com.orange.person.domain.UserAuthentication;
import com.orange.person.domain.UserBase;
import com.orange.person.dto.AuthenticationDto;
import com.orange.person.info.AuditPageInfo;
import com.orange.person.service.UserAuthenticationService;
import com.orange.share.page.SortUtil;
import com.orange.share.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    @Autowired
    UserAuthenticationDao authenticationDao;
    @Autowired
    UserBaseDao userBaseDao;
    @Override
    public UserAuthentication addUserAuthentication(UserAuthentication authentication) {
        String  userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authentication.setUserId(userId);
        authentication.setCreateTime(System.currentTimeMillis()/1000);
        authentication.setAuditStatus(AuditStatus.NO_AUDIT.getCode());
        authentication.setAuthenticationGrade(AuthenticationGrade.UNCERTIFIED_MANDATORY.getCode());
        authenticationDao.saveAndFlush(authentication);
        return authentication;
    }

    @Override
    public UserAuthentication getAuthentication() {
        String  userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authenticationDao.findOne(userId);
    }

    @Override
    public Integer getAuthenticationGrade(String userId) {
        UserAuthentication userAuthentication= authenticationDao.findByUserIdAndAuditStatus(userId,AuditStatus.AUDIT_SUCCESS.getCode());
        if (userAuthentication==null){
            return null;
        }else {
            return userAuthentication.getAuditStatus();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserAuthentication upAuthentication(AuthenticationDto authenticationDto) {
        UserAuthentication userAuthentication =authenticationDao.findOne(authenticationDto.getUserId());
        UserBase userBase = userBaseDao.findOne(authenticationDto.getUserId());
        if (authenticationDto.getAuditStatus() == AuditStatus.AUDIT_SUCCESS.getCode()){
            userAuthentication.setAuditStatus(AuditStatus.AUDIT_SUCCESS.getCode());
            userBase.setAuthenticationGrade(AuthenticationGrade.UNCERTIFIED_MANDATORY.getCode());
        }else {
            userAuthentication.setAuditStatus(AuditStatus.AUDIT_FAIL.getCode());
            userBase.setAuthenticationGrade(AuthenticationGrade.UNCERTIFIED.getCode());
        }
        userAuthentication.setAuditRemark(authenticationDto.getAuditRemark());

        authenticationDao.saveAndFlush(userAuthentication);
        userBaseDao.saveAndFlush(userBase);
        return userAuthentication;
    }

    @Override
    public PageVo getPage(AuditPageInfo auditPageInfo) {
        Page<UserAuthentication> page= authenticationDao.findAll(AuthenticationSpec.method1(auditPageInfo.getStatus()), SortUtil.buildDESC(auditPageInfo.getPage(),auditPageInfo.getSize(), Sort.Direction.DESC,"createTime"));
        return new PageVo(page.getTotalPages(),auditPageInfo.getPage(),page.getTotalElements(),page.getContent());
    }

    @Override
    public UserAuthentication getByUserId(String userId) {

        return authenticationDao.findOne(userId);
    }

    public static class AuthenticationSpec {
        public static Specification<UserAuthentication> method1(Integer status) {
            return new Specification<UserAuthentication>() {
                @Override
                public Predicate toPredicate(Root<UserAuthentication> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Path<Boolean> enable = root.get("enable");
                    Path<Integer> auditStatus = root.get("auditStatus");
                    Predicate predicate;
                    if (status==AuditStatus.ALL.getCode()){
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true));

                    }else {
                        predicate = criteriaBuilder.and(criteriaBuilder.equal(enable, true),criteriaBuilder.equal(auditStatus, status));

                    }
                    return predicate;
                }
            };
        }
    }
}

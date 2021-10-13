package com.orange.shop.service.impl;

import com.orange.person.dao.UserBaseDao;
import com.orange.person.domain.UserBase;
import com.orange.share.page.PageInfo;
import com.orange.share.page.SortUtil;
import com.orange.share.vo.PageVo;
import com.orange.shop.dao.ProductCommentDao;
import com.orange.shop.domain.ProductComment;
import com.orange.shop.info.ProductCommentPageInfo;
import com.orange.shop.service.ProductCommentService;
import com.orange.shop.vo.ProductCommentVo;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.method.P;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductCommentServiceImpl implements ProductCommentService {
    @Autowired
    ProductCommentDao productCommentDao;
    @Autowired
    UserBaseDao userBaseDao;
    @Override
    public ProductComment addProductComment(ProductComment comment) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setCreateTime(System.currentTimeMillis()/1000);
        comment.setUserId(userId);
        productCommentDao.save(comment);
        return comment;
    }

    @Override
    public PageVo getPageProductComment(ProductCommentPageInfo pageInfo) {
        Page<ProductComment> page = productCommentDao.findAllByEnableAndProductId(true,pageInfo.getProductId(),SortUtil.buildDESC(pageInfo.getPage(),pageInfo.getSize(), Sort.Direction.DESC,"createTime"));
        List<ProductCommentVo> comments = new ArrayList<>();
        for (ProductComment comment : page.getContent()) {
            ProductCommentVo commentVo= new ProductCommentVo();
            BeanUtils.copyProperties(comment,commentVo);
            UserBase userBase = userBaseDao.findOne(comment.getUserId());
            if (userBase!=null){
                commentVo.setUserHead(userBase.getPortraitUrl());
                commentVo.setNickname(userBase.getNickname());
            }
            comments.add(commentVo);
        }
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),comments);
    }

    @Override
    public void delProductComment(String id) {
        ProductComment comment = productCommentDao.findOne(id);
        comment.setEnable(false);
        productCommentDao.save(comment);
    }

}

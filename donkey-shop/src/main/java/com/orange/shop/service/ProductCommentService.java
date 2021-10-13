package com.orange.shop.service;

import com.orange.share.page.PageInfo;
import com.orange.share.vo.PageVo;
import com.orange.shop.domain.ProductComment;
import com.orange.shop.info.ProductCommentPageInfo;

public interface ProductCommentService {
    ProductComment addProductComment(ProductComment comment);
    PageVo getPageProductComment(ProductCommentPageInfo pageInfo);
    void delProductComment(String id);
}

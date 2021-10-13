package com.orange.shop.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProductCommentVo {
    String userHead;
    String nickname;
    Long createTime;
    String commentContent;



    List<String> imgUrl;
}

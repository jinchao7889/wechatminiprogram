package com.orange.shop.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;

@Document
@Data
public class ProductComment {
    @Id
    String id;
    @Column(name = "user_id")
    String userId;
    @Column(name = "order_id")
    String orderId;
    @Column(name = "comment_content")
    String commentContent;

    @Column(name = "create_time")
    Long createTime;

    @Column(name = "enable")
    Boolean enable = true;

    @Column(name = "img_url")
    List<String> imgUrl;

    @Column(name = "product_id")
    Long productId;
}

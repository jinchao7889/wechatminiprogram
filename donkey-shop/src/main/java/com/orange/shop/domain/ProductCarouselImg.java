package com.orange.shop.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product_carousel_img")
public class ProductCarouselImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    /**
     * 商品Id
     */
    @Column(name = "product_id")
    Long productId;
    @Column(name = "img_url")
    String imgUrl;
    /**
     * 序号，用于排序
     */
    @Column(name = "serial_number")
    Integer serialNumber;
}

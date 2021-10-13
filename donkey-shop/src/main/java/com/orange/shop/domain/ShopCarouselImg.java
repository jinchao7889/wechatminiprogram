package com.orange.shop.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 商铺轮播图
 */
@Data
@Entity
@Table(name = "shop_carousel_img")
public class ShopCarouselImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    /**
     * 商品Id
     */
    @Column(name = "shop_id")
    Integer shopId;
    @Column(name = "img_url")
    String imgUrl;
    /**
     * 序号，用于排序
     */
    @Column(name = "serial_number")
    Integer serialNumber;

    /**
     * 轮播图状态
     * 是否上架
     * 1上架 2下架
     */
    @Column(name = "upper_shelf")
    Boolean upperShelf;

    /**
     * 链接类型
     * 1.外部链接
     * 2.内部链接
     */
    @Column(name = "link_type")
    Integer linkType;


    /**
     * 携带参数
     */
    @Column(name = "portability_parameter")
    String portabilityParameter;
    /**
     * 图片链接
     */
    @Column(name = "chart_link")
    String chartLink;

    /**
     * 轮播图链接
     */
    @Column(name = "chart_url")
    String chartUrl;
    @Column(name = "enable")
    Boolean enable=true;
    @Column(name = "create_time")
    Long createTime;
}

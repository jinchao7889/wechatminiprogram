package com.orange.shop.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 店铺表
 */
@Data
@Entity
@Table(name = "tb_shop")
public class Shop {
    /**
     * 店铺ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    /**
     * 店铺名字
     */
    @Column(name = "shop_name")
    String shopName;

    @Column(name = "shop_img")
    String shopImg;

    @Column(name = "shop_url")
    String shopUrl;

    @Column(name = "logo_img")
    String logoImg;
}

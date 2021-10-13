package com.orange.shop.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 商品类别表
 */
@Data
@Entity
@Table(name = "product_type")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    /**
     * 类别名称
     */
    @Column(name = "type_name",length = 30)
    String typeName;

    /**
     * 类别名称
     */
    @Column(name = "type_e_name",length = 30)
    String typeEName;

    /**
     * 店铺的Id
     */
    @Column(name = "shop_id")
    Integer shopId;

    @Column(name = "type_img")
    String typeImg;
}

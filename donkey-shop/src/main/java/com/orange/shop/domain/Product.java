package com.orange.shop.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 商品表
 */
@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    /**
     * 商品名称
     */
    @Column(name = "product_name")
    String productName;

    /**
     * 商品价格
     */
    @Column(name = "product_price")
    BigDecimal productPrice;

    /**
     * 商品描述
     */
    @Column(name = "product_describe")
    String productDescribe;

    /**
     * 商品缩略图
     */
    @Column(name = "product_thumbnail")
    String productThumbnail;

    /**
     * 是否上架
     */
    @Column(name = "is_upper_shelf")
    Boolean isUpperShelf;

    /**
     * 商品规格
     */
    @Column(name = "product_spec")
    String productSpec;
    /**
     * 商品销量
     */
    @Column(name = "product_sail")
    Integer productSail;

    /**
     * 商品重量
     */
    @Column(name = "product_weight")
    Integer productWeight;

    /**
     * 商品库存
     */
    @Column(name = "product_stock")
    Integer productStock;

    /**
     * 商户ID
     */
    @Column(name = "shop_id")
    Integer shopId;

    @Column(name = "product_type_id")
    Integer productTypeId;

    /**
     * 是否热销
     */
    @Column(name = "is_sell_well")
    Boolean isSellWell;

    /**
     * 是否删除
     */
    @Column(name = "is_enable")
    Boolean isEnable=true;

    /**
     *
     *
     * 是否为实体商品
     *
     */
    @Column(name = "product_property")
    Boolean productEntity;

    @Column(name = "cover_img")
    String coverImg;

    /**
     * 运费
     */
    @Column(name = "deliver_money")
    BigDecimal deliverMoney;
    /**
     * 配送方式
     */
    @Column(name = "deliver_type")
    Integer  deliverType;

    /**
     * 商品销售类型
     */
    @Column(name = "product_sale_type")
    Integer productSaleType;

    /**
     * 商品押金
     */
    @Column(name = "product_deposit")
    BigDecimal productDeposit;
    /**
     * 是否有规格
     */
    @Column(name = "is_spec")
    Boolean isSpec=false;

    /**
     * 排列序号
     */
    @Column(name = "serial_number")
    Integer serialNumber = 2;
}

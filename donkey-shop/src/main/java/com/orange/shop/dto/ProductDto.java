package com.orange.shop.dto;

import com.orange.shop.domain.ProductAdditionalCharges;
import com.orange.shop.domain.ProductCarouselImg;
import com.orange.shop.domain.ProductSpecs;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    Long id;

    String productName;

    /**
     * 商品价格
     */
    BigDecimal productPrice;



    /**
     * 商品描述
     */
    String productDescribe;

    /**
     * 商品缩略图
     */
    String productThumbnail;

    /**
     * 是否上架
     */
    Boolean isUpperShelf;

    /**
     * 商品规格
     */
    String productSpec;
    /**
     * 商品销量
     */
    Integer productSail;

    /**
     * 商品重量
     */
    Integer productWeight;

    /**
     * 商品库存
     */
    Integer productStock;

    /**
     * 商户ID
     */
    Integer shopId;

    Integer productTypeId;

    /**
     * 是否热销
     */
    Boolean isSellWell;

    /**
     * 是否删除
     */
    Boolean isEnable=true;

    String coverImg;

    /**
     * 运费
     */
    BigDecimal deliverMoney;
    /**
     * 配送方式
     */
    Integer  deliverType;

    /**
     *商品轮播图
     */
    List<ProductCarouselImg> carouselImg;

    /**
     * 附加收费项目
     */
    List<ProductAdditionalCharges> additionalCharges;

    List<ProductSpecs> productSpecs;

    BigDecimal productDeposit;

    String detailContent;
    Integer productSaleType;
    Boolean productEntity;
    Boolean isSpec=false;
    Integer serialNumber ;
}

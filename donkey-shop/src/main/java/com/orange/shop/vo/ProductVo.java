package com.orange.shop.vo;

import com.orange.shop.domain.ProductSpecs;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductVo {
    Long id;
    String productName;
    BigDecimal productPrice;
    Boolean isSellWell;
    String coverImg;
    String productDescribe;
    Integer productStock;
    Integer productSail;
    Boolean isUpperShelf;
    Integer productTypeId;
    String productType;
    Integer shopId;
    Integer serialNumber;
}

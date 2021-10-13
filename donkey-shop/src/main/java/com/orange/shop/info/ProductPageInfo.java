package com.orange.shop.info;

import com.orange.share.page.PageInfo;
import lombok.Data;


public class ProductPageInfo extends PageInfo {
    private Integer shopId;
    /**
     * 商品类型
     */
    private Integer productTypeId;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }
}

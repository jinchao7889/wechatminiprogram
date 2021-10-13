package com.orange.shop.constants;

/**
 * 商品销售类型表
 */
public enum  ProductSaleType {
    ALL(0,"所有售卖类型"),
    SALE_TYPE(1,"售卖商品"),
    LEASE_TYPE(2,"租赁商品");
    String desc;
    int code;
    ProductSaleType(int code,String desc){
        this.code=code;
        this.desc=desc;
    }

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }
}

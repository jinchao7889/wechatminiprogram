package com.orange.activity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ActivityVo {
    String id;
    String title;
    Long startActivity;
    Integer maxPeopleNumber;
    BigDecimal earnestMoney;

    /**
     * 价格
     */
    BigDecimal price;
    String collectionPlace;
    Integer activityStatus;
    String coverMap;
}

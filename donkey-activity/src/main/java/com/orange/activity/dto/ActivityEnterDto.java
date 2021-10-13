package com.orange.activity.dto;

import com.orange.order.dto.OrderDto;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ActivityEnterDto {
    String activityId;

    String userId;


    /**
     * 联系电话
     */
    String contactNumber;
    /**
     * 凉席人身份证号码
     */
    String contactId;

    String gender;
    /**
     * 真实姓名
     */
    String realName;
    /**
     * QQ号码
     */
    String qqNumber;
    /**
     * 微信号
     */
    String wxNumber;

    OrderDto orderDto;
    @NotNull
    Integer enterNumber;

}

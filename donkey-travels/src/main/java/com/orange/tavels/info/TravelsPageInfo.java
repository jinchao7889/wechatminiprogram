package com.orange.tavels.info;

import com.orange.share.page.PageInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TravelsPageInfo extends PageInfo {
    @NotNull
    Integer status;

    /**
     * 排序条件
     * 1 . 最新
     * 2 . 消费
     * 3 . 天数
     */
    Integer condition=1;
}

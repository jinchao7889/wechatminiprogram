package com.orange.trip.info;

import com.orange.share.page.PageInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ElementPageInfo extends PageInfo {
    @NotNull
    Integer tripInventoryId;
}

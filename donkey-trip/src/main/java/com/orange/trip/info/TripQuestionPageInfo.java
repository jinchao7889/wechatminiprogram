package com.orange.trip.info;

import com.orange.share.page.PageInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TripQuestionPageInfo extends PageInfo {
    @NotNull
    Integer tripId;
    @NotNull
    Integer questionStatus;
}

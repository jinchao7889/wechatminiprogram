package com.orange.trip.dto;

import com.orange.trip.domain.TripDetailed;
import com.orange.trip.info.TripSummarizeInfo;
import lombok.Data;

import java.util.List;

@Data
public class TripSummarizeDto  extends TripSummarizeInfo {
    List<TripDetailed> tripDetail;
}

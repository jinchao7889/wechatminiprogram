package com.orange.trip.service;

import com.orange.share.vo.PageVo;
import com.orange.trip.domain.TripExpensesDetailed;
import com.orange.trip.info.SummarizePageInfo;
import com.orange.trip.info.TripExpensesDetailedInfo;
import com.orange.trip.vo.TripExpensesVo;

import java.util.List;

/**
 * 行程花销详细
 */
public interface TripExpensesDetailedService {
    TripExpensesDetailed addTripExpenses(TripExpensesDetailedInfo detailedInfo);

    void delTripExpensesDetailed(Integer id);


}

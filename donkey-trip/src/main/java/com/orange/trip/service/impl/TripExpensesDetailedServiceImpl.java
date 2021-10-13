package com.orange.trip.service.impl;

import com.orange.share.page.SortUtil;
import com.orange.share.vo.PageVo;
import com.orange.trip.dao.TripExpensesDetailedDao;
import com.orange.trip.dao.TripSummarizeDao;
import com.orange.trip.domain.TripExpensesDetailed;
import com.orange.trip.domain.TripSummarize;
import com.orange.trip.info.SummarizePageInfo;
import com.orange.trip.info.TripExpensesDetailedInfo;
import com.orange.trip.service.TripExpensesDetailedService;
import com.orange.trip.vo.TripExpensesElementVo;
import com.orange.trip.vo.TripExpensesVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TripExpensesDetailedServiceImpl implements TripExpensesDetailedService {
    @Autowired
    TripExpensesDetailedDao detailedDao;
    @Autowired
    TripSummarizeDao tripSummarizeDao;


    @Transactional
    @Override
    public TripExpensesDetailed addTripExpenses(TripExpensesDetailedInfo detailedInfo) {
        TripExpensesDetailed detailed = new TripExpensesDetailed();
        BeanUtils.copyProperties(detailedInfo,detailed);
        detailedDao.save(detailed);
        return detailed;
    }

    @Override
    public void delTripExpensesDetailed(Integer id) {
        detailedDao.delete(id);
    }


}

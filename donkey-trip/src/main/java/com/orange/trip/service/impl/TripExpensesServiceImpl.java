package com.orange.trip.service.impl;

import com.orange.trip.dao.TripExpensesDao;
import com.orange.trip.dao.TripExpensesDetailedDao;
import com.orange.trip.domain.TripExpenses;
import com.orange.trip.domain.TripExpensesDetailed;
import com.orange.trip.dto.TripExpensesDto;
import com.orange.trip.service.TripExpensesService;
import com.orange.trip.vo.TripExpensesToVo;
import com.orange.trip.vo.TripExpensesVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TripExpensesServiceImpl implements TripExpensesService {
    @Autowired
    TripExpensesDao tripExpensesDao;
    @Autowired
    TripExpensesDetailedDao expensesDetailedDao;

    @Override
    public List<TripExpensesVo> add(List<TripExpensesDto> expensesList) {
        List<TripExpensesVo> tripExpensesVo = new ArrayList<>();
        for (TripExpensesDto expensesDto: expensesList){
            TripExpenses tripExpenses = new TripExpenses();
            BeanUtils.copyProperties(expensesDto,tripExpenses);
            tripExpenses.setTripId(expensesDto.getTripId());
            tripExpensesDao.saveAndFlush(tripExpenses);
            TripExpensesVo expensesVo = new TripExpensesVo();
            BeanUtils.copyProperties(tripExpenses,expensesVo);
            List<TripExpensesDetailed> expensesDetaileds = new ArrayList<>();
            for (TripExpensesDetailed expensesDetailed: expensesDto.getExpensesDetail()){
                expensesDetailed.setTripExpensesId(tripExpenses.getId());
                expensesDetailedDao.saveAndFlush(expensesDetailed);
                expensesDetaileds.add(expensesDetailed);
            }
            expensesVo.setExpensesDetail(expensesDetaileds);
            tripExpensesVo.add(expensesVo);
        }
        return tripExpensesVo;
    }

    @Override
    public TripExpensesToVo getAll(Long tripId) {
        List<TripExpensesVo> tripExpensesVo = new ArrayList<>();
        BigDecimal totalMoney = new BigDecimal(0);
        TripExpensesToVo tripExpensesToVo = new TripExpensesToVo();
        List<TripExpenses> tripExpenses= tripExpensesDao.findAllByTripId(tripId);
        for (TripExpenses expenses:tripExpenses){
            TripExpensesVo expensesVo = new TripExpensesVo();
            BeanUtils.copyProperties(expenses,expensesVo);
            List<TripExpensesDetailed> tripExpensesDetaileds =  expensesDetailedDao.findAllByTripExpensesId(expenses.getId());
            expensesVo.setExpensesDetail(tripExpensesDetaileds);
            totalMoney=totalMoney.add(expensesVo.getTotalMoney());

            tripExpensesVo.add(expensesVo);
        }
        tripExpensesToVo.setList(tripExpensesVo);
        tripExpensesToVo.setTotalMoney(totalMoney);
        return tripExpensesToVo;
    }

    @Override
    public void delExpenses(Integer id) {
        tripExpensesDao.delete(id);
    }
}

package com.orange.trip.service;

import com.orange.trip.domain.TripExpenses;
import com.orange.trip.dto.TripExpensesDto;
import com.orange.trip.vo.TripExpensesToVo;
import com.orange.trip.vo.TripExpensesVo;

import java.util.List;

public interface TripExpensesService {
    List<TripExpensesVo>  add(List<TripExpensesDto> expensesList);
    TripExpensesToVo getAll(Long tripId);
    void delExpenses(Integer id);
}

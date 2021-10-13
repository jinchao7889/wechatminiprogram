package com.orange.service;

import com.orange.domain.RotationChart;
import com.orange.vo.RotationChartMainPageVo;

import java.util.List;

public interface RotationChartService {
    RotationChart addChart(RotationChart chart);
    List<RotationChart> addCharts(List<RotationChart>  charts);
    List<RotationChart> getAll(Integer application);
    void delChart(Long id);
    List<RotationChart> getMgAll(Integer application);
    RotationChartMainPageVo getMainPageChart();
}

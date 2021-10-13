package com.orange.service.impl;

import com.orange.dao.RotationChartDao;
import com.orange.domain.RotationChart;
import com.orange.service.RotationChartService;
import com.orange.vo.RotationChartMainPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RotationChartServiceImpl implements RotationChartService {
    @Autowired
    RotationChartDao chartDao;
    @Override
    public RotationChart addChart(RotationChart chart) {
        chart.setCreateTime(System.currentTimeMillis()/1000);
        chartDao.saveAndFlush(chart);
        return chart;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<RotationChart> addCharts(List<RotationChart> charts) {
        for (RotationChart chart : charts){
            chartDao.saveAndFlush(chart);
        }
        return charts;
    }

    @Override
    public List<RotationChart> getAll(Integer application) {
        return  chartDao.findAllByUpperShelfAndApplicationFunction(true,application,new Sort(Sort.Direction.DESC,"OrderNumber"));
    }

    @Override
    public void delChart(Long id) {
        chartDao.delete(id);
    }

    @Override
    public List<RotationChart> getMgAll(Integer application) {
        return  chartDao.findAllByApplicationFunction(application,new Sort(Sort.Direction.DESC,"OrderNumber"));
    }

    @Override
    public RotationChartMainPageVo getMainPageChart() {
        RotationChartMainPageVo rotationChartMainPageVo = new RotationChartMainPageVo();
        rotationChartMainPageVo.setMainChart(chartDao.findAllByUpperShelfAndApplicationFunction(true,1,new Sort(Sort.Direction.DESC,"OrderNumber")));
        rotationChartMainPageVo.setTripChart(chartDao.findAllByUpperShelfAndApplicationFunction(true,2,new Sort(Sort.Direction.DESC,"OrderNumber")));
        return rotationChartMainPageVo;
    }
}

package com.orange.controller;

import com.orange.domain.RotationChart;
import com.orange.service.RotationChartService;
import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rotation_chart")
public class RotationChartController {
    @Autowired
    RotationChartService chartService;

    @PostMapping("/add")
    public ResponseWrapper addChart(@RequestBody RotationChart chart){
        return ResponseWrapper.markSuccess(chartService.addChart(chart));
    }
    @PostMapping("/adds")
    public ResponseWrapper addCharts(@RequestBody List<RotationChart> chart){
        return ResponseWrapper.markSuccess(chartService.addCharts(chart));
    }
    @GetMapping("/get/{type}")
    public ResponseWrapper getChart(@PathVariable Integer type){
        return ResponseWrapper.markSuccess(chartService.getAll(type));
    }
    @GetMapping("/get/mg/{type}")
    public ResponseWrapper getMgChart(@PathVariable Integer type){
        return ResponseWrapper.markSuccess(chartService.getMgAll(type));
    }
    @GetMapping("/get_main_page_chart")
    public ResponseWrapper getMainPageChart(){
        return ResponseWrapper.markSuccess(chartService.getMainPageChart());
    }


    @GetMapping("/del/{id}")
    public ResponseWrapper getChart(@PathVariable Long id){
        chartService.delChart(id);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }
}

package com.orange.vo;

import com.orange.domain.RotationChart;
import lombok.Data;

import java.util.List;

@Data
public class RotationChartMainPageVo {
    List<RotationChart> tripChart;
    List<RotationChart> mainChart;
}

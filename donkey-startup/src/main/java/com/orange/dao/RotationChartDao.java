package com.orange.dao;

import com.orange.domain.RotationChart;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RotationChartDao extends JpaRepository<RotationChart,Long> {
    List<RotationChart> findAllByUpperShelfAndApplicationFunction(Boolean upperShelf,Integer applicationFunction, Sort sort);
    List<RotationChart> findAllByApplicationFunction(Integer applicationFunction, Sort sort);

}

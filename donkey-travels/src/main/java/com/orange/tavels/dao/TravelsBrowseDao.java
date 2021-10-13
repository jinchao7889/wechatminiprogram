package com.orange.tavels.dao;

import com.orange.tavels.domain.TravelsBrowse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TravelsBrowseDao  extends JpaRepository<TravelsBrowse,Integer> {
    TravelsBrowse findByUserIdAndTravelsId(String userId,String travelsId);

    @Query("select count (*) from TravelsBrowse t where t.travelsId=:ti")
    Long getCountByTravelsId(@Param("ti") String travelsId);
}

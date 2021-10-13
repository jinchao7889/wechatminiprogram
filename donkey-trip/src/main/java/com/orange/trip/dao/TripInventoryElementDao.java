package com.orange.trip.dao;

import com.orange.trip.domain.TripInventoryElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TripInventoryElementDao extends JpaRepository<TripInventoryElement,Integer>, JpaSpecificationExecutor<TripInventoryElement> {
    List<TripInventoryElement> findAllByInventoryId(Integer inventoryId);

    @Modifying
    @Query("UPDATE TripInventoryElement e set e.isCheck=:ic where e.id=:id")
    void upElementCheck(@Param("id") Integer id,@Param("ic") Boolean isCheck);
}

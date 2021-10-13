package com.orange.trip.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 行程清单
 */
@Table(name = "trip_inventory")
@Entity
@Data
public class TripInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "trip_id")
    Long tripId;
    /**
     * 清单属性
     */
    @Column(name = "inventory_type")
    String inventoryType;
}

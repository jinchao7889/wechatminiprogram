package com.orange.trip.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 行程花销详细
 */
@Table(name = "trip_inventory_element")
@Entity
@Data
public class TripInventoryElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "inventory_id")
    Integer inventoryId;

    @Column(name = "inventory_element")
    String inventoryElement;

    @Column(name = "is_check")
    Boolean isCheck;

    @Column(name = "serial_number")
    Integer serialNumber;
}

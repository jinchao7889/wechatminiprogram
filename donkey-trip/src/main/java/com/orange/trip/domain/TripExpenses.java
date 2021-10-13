package com.orange.trip.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * 行程花销
 */
@Data
@Table(name = "trip_expenses")
@Entity
public class TripExpenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "trip_id")
    Long tripId;
    String tripTime;
    BigDecimal totalMoney;
    @Column(name = "serial_number")
    Integer serialNumber;
}

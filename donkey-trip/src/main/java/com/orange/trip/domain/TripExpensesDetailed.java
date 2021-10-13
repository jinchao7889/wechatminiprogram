package com.orange.trip.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 行程花销详细
 */
@Data
@Table(name = "trip_expenses_detailed")
@Entity
public class TripExpensesDetailed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    /**
     * 行程大概的Id
     */
    @Column(name = "trip_expenses_id")
    Integer tripExpensesId;

    /**
     * 对应的条目
     */
    String entry;

    /**
     * 对应的花销
     */
    BigDecimal expenses;

    @Column(name = "serial_number")
    Integer serialNumber;
}

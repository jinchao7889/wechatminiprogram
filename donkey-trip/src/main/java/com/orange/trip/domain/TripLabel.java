package com.orange.trip.domain;

import lombok.Data;

import javax.persistence.*;


/**
 * 行程标签
 */
@Data
@Entity
@Table(name = "trip_label")
public class TripLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "label_name",length = 20)
    String  labelName;
}

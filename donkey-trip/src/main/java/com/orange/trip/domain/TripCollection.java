package com.orange.trip.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "trip_collection")
public class TripCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "trip_id")
    Long tripId;
    @Column(name = "user_id")
    String userId;
    @Column(name = "create_time")
    Long createTime;
}

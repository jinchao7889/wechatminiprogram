package com.orange.trip.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    @Column(name = "user_id",length = 32)
    String userId;

    @Column(name = "travels_id",length = 32)
    String travelsId;
    /**
     * 收藏量
     */
    @Column(name = "collection_volume")
    Integer collectionVolume=0;

    /**
     * 封面图
     */
    @Column(name = "cover_map")
    String coverMap;



    @Column(name = "create_time")
    Long createTime;

}

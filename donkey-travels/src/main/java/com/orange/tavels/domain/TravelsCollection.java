package com.orange.tavels.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 游记收藏(点赞功能表)
 */
@Data
@Entity
@Table(name = "travels_collection")
public class TravelsCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "travels_id",length = 32)
    String travelsId;

    @Column(name = "user_id",length = 32)
    String userId;

    @Column(name = "create_time",length = 32)
    Long createTime;

    Boolean enable;
}

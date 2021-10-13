package com.orange.trip.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 点赞表
 */
@Entity
@Table(name = "tb_fabulous")
@Data
public class Fabulous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "user_id")
    String userId;

    /**
     * 对应的回答的ID
     */
    Integer answerId;
}

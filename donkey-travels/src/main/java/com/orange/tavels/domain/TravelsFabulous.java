package com.orange.tavels.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 游记点赞表
 */
@Data
@Entity
@Table(name = "travels_fabulous")
public class TravelsFabulous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "user_id")
    String userId;

    /**
     * 对应的回答的ID
     */
    Integer commentId;
}

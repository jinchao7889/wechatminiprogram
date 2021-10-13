package com.orange.tavels.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 游记浏览表
 */
@Data
@Entity
@Table(name = "travels_browse")
public class TravelsBrowse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    /**
     * 游记的Id
     */
    @Column(name = "travels_id",length = 32)
    String travelsId;

    @Column(name = "user_id",length = 32)
    String userId;

    @Column(name = "create_time",length = 32)
    Long createTime;
}

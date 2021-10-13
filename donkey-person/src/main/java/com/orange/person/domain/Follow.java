package com.orange.person.domain;

import lombok.Data;

import javax.persistence.*;

@Table(name = "tb_follow")
@Entity
@Data
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "concern")
    private String concern;

    @Column(name = "be_concerned",length = 32)
    private String beConcerned;

    @Column(name = "create_time")
    Long createTime;
}

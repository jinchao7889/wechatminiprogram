package com.orange.person.domain;

import lombok.Data;

import javax.persistence.*;

@Table(name = "tb_userconnection")
@Entity
@Data
public class UserConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "userId",length = 50)
    String userId;
    /**
     * 提供商ID
     */
    @Column(name = "provider_id")
    String providerId;
    /**
     * OPEN_ID
     */
    @Column(name = "provider_user_id")
    String providerUserId;
    @Column(name = "rank")
    Integer rank;
    @Column(name = "display_name")
    String displayName;
    @Column(name = "profile_url")
    String profileUrl;
    @Column(name = "image_url")
    String imageUrl;

}

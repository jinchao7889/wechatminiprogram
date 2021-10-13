package com.orange.person.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "address_management")
public class AddressManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id",length = 32)
    private String userId;
    @Column(name = "contacts",length = 20)
    @NotBlank
    private String contacts;
    @Column(name = "phone",length = 20)
    @NotBlank
    private String phone;
    @Column(name = "approximately_address",length = 50)
    @NotBlank
    private String approximatelyAddress;
    @Column(name = "detailed_address",length = 50)
    @NotBlank
    private String detailedAddress;

    @Column(name = "address_default",length = 50)
    @NotNull
    private boolean addressDefault=false;
    private boolean enable=true;

    private Long createTime;
    @Column(name = "update_time")
    private Long updateTime;
}

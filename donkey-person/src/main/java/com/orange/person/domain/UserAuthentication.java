package com.orange.person.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_authentication")
public class UserAuthentication {
    @Id
    String userId;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    String realName;

    /**
     * 用户电话
     */
    @Column(name = "user_phone")
    String userPhone;

    /**
     * 身份证正面照片
     */
    @Column(name = "id_card_positive_photo")
    String idCardPositivePhoto;

    /**
     * 反面照片
     */
    @Column(name = "id_card_negative_photo")
    String idCardNegativePhoto;

    /**
     * 学生证照片
     */
    @Column(name = "student_id_photo")
    String studentIdPhoto;

    Boolean enable=true;

    @Column(name = "authentication_grade")
    Integer authenticationGrade;

    @Column(name = "audit_status")
    Integer auditStatus;
    @Column(name = "audit_remark")
    String auditRemark;
    @Column(name = "create_time")
    Long createTime;
}

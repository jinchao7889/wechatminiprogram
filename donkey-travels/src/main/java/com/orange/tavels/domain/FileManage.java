package com.orange.tavels.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "file_manage")
public class FileManage {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    String id;

    @Column(name = "file_url")
    String fileUrl;

    @Column(name = "user_id")
    String userId;

    @Column(name = "create_time")
    Long createTime;

    @Column(name = "cos_key")
    String cosKey;
}

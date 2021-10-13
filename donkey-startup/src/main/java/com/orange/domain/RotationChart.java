package com.orange.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Map;

/**
 * 轮播图
 */
@Entity
@Table(name = "rotation_chart")
@Data
public class RotationChart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * 轮播图状态
     * 是否上架
     * 1上架 2下架
     */
    @Column(name = "upper_shelf")
    Boolean upperShelf;

    /**
     * 链接类型
     * 1.外部链接
     * 2.内部链接
     */
    @Column(name = "link_type")
    Integer linkType;
    /**
     * 排列序号
     */
    @Column(name = "order_number")
    Integer orderNumber;
    /**
     * 携带参数
     */
    @Column(name = "portability_parameter")
    String portabilityParameter;
    /**
     * 图片链接
     */
    @Column(name = "chart_link")
    String chartLink;

    /**
     * 轮播图链接
     */
    @Column(name = "chart_url")
    String chartUrl;
    @Column(name = "enable")
    Boolean enable=true;
    @Column(name = "create_time")
    Long createTime;

    /**
     * 1.首页轮播
     * 2.行程轮播
     */
    @Column(name = "application_function")
    Integer applicationFunction;
}

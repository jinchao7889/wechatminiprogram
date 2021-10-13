package com.orange.shop.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 商品附加收费项
 */
@Data
@Entity
@Table(name = "product_additional_charges")
public class ProductAdditionalCharges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "product_id")
    Long productId;

    /**
     * 附加收费名称
     */
    @Column(name = "product_name")
    String additionalChargesName;

    /**
     * 收费价格
     */
    @Column(name = "charges_price")
    BigDecimal chargesPrice;

    /**
     * 可选等级
     * 1.可选
     * 2.必选
     * 3.认证后可选
     */
    @Column(name = "optional_grade")
    Integer optionalGrade=1;

    /**
     * 费用说明
     */
    String chargesExplain;
}

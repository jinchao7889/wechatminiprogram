package com.orange.shop.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product_specs")
public class ProductSpecs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "shop_id")
    Integer shopId;
    @Column(name = "product_id")
    Long productId;
    @Column(name = "specs_name")
    String specsName;
    BigDecimal price;

    Boolean isDefault=false;
    Boolean enable=true;
}

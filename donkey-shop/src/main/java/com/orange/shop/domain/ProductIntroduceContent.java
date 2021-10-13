package com.orange.shop.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class ProductIntroduceContent {
    Long id;
    String content;
}

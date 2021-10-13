package com.orange.tavels.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class TravelsContent {
    String id;
    String content;
}

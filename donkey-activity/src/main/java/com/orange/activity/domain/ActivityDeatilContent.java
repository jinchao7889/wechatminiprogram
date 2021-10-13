package com.orange.activity.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class ActivityDeatilContent {
    String id;
    String content;
}

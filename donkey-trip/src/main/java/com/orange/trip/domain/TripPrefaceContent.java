package com.orange.trip.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
public class TripPrefaceContent {
    Long id;
    String content;
}

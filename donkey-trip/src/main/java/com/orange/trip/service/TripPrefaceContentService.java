package com.orange.trip.service;

import com.orange.trip.domain.TripPrefaceContent;

public interface TripPrefaceContentService {
    TripPrefaceContent addTripPrefaceContent(TripPrefaceContent content);

    TripPrefaceContent  findTripPrefaceContent(Long id);
}

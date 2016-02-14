package com.epam.spring.theater.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EventStatistic {
    @NonNull
    private String eventName;
    private int accessedCount;
    private int priceQueryCount;
    private int bookedCount;
}

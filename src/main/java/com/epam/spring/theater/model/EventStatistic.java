package com.epam.spring.theater.model;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class EventStatistic {
    private Integer eventStatisticId;
    @NonNull
    private String eventName;
    private int accessedCount;
    private int priceQueryCount;
    private int bookedCount;
}

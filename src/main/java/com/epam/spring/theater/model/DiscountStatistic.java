package com.epam.spring.theater.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@RequiredArgsConstructor
public class DiscountStatistic {
    @NonNull
    private Integer userId;
    private DiscountType discountType;
    private Integer count;
}

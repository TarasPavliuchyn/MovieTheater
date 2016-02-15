package com.epam.spring.theater.model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class DiscountStatistic {
    private static final AtomicInteger count = new AtomicInteger(0);
    private Integer discountStatisticId;
    private DiscountType discountType;
    private int appliedCount;
    private Integer userId;

    public DiscountStatistic(DiscountType discountType, Integer userId) {
        this.discountStatisticId = count.incrementAndGet();
        this.discountType = discountType;
        this.userId = userId;
    }
}

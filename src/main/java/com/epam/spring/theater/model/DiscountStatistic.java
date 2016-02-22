package com.epam.spring.theater.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@NoArgsConstructor
public class DiscountStatistic {
    private Integer discountStatisticId;
    private DiscountType discountType;
    private int appliedCount;
    private Integer userId;

    public DiscountStatistic(DiscountType discountType, Integer userId) {
        this.discountType = discountType;
        this.userId = userId;
    }
}

package com.epam.spring.theater.dao;

import com.epam.spring.theater.model.DiscountStatistic;
import com.epam.spring.theater.model.DiscountType;

public interface DiscountStatisticDao extends CrudDao<DiscountStatistic, Integer> {
    DiscountStatistic findByUserIdAndType(Integer userId, DiscountType discountType);
}

package com.epam.spring.theater.dao.impl;

import com.epam.spring.theater.dao.CrudDaoImpl;
import com.epam.spring.theater.dao.DiscountStatisticDao;
import com.epam.spring.theater.model.DiscountStatistic;
import com.epam.spring.theater.model.DiscountType;
import org.springframework.stereotype.Repository;

@Repository
public class DiscountStatisticDaoImpl extends CrudDaoImpl<DiscountStatistic, Integer> implements DiscountStatisticDao {

    @Override
    public DiscountStatistic findByUserIdAndType(Integer userId, DiscountType discountType) {
        return getStorage().values()
                .stream()
                .filter(statistic -> statistic.getUserId().equals(userId) && statistic.getDiscountType() == discountType)
                .findFirst().orElse(null);
    }
}

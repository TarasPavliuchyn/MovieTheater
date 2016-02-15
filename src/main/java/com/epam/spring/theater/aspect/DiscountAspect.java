package com.epam.spring.theater.aspect;

import com.epam.spring.theater.dao.impl.DiscountStatisticDaoImpl;
import com.epam.spring.theater.dao.impl.EventStatisticDaoImpl;
import com.epam.spring.theater.model.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Aspect
@Component
public class DiscountAspect {

    @Autowired
    private DiscountStatisticDaoImpl discountStatisticDao;

    @AfterReturning("execution(* com.epam.spring.theater.service.DiscountService.getDiscount(..)) && args(user, event, date)")
    private void countDiscountStatistic(JoinPoint jp, User user, Event event, Date date) {
        Optional<DiscountStatistic> optionalStatistic = Optional.ofNullable(discountStatisticDao.find(user.getUserId()));
        DiscountStatistic eventStatistic = incrementDiscountCount(optionalStatistic.orElse(new DiscountStatistic(user.getUserId())));
        discountStatisticDao.createOrUpdate(user.getUserId(), eventStatistic);
    }


    private DiscountStatistic incrementDiscountCount(DiscountStatistic eventStatistic) {
        int priceQueryCount = eventStatistic.getPriceQueryCount() + 1;
        eventStatistic.setPriceQueryCount(priceQueryCount);
        return eventStatistic;
    }
}

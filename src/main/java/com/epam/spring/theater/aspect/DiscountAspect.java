package com.epam.spring.theater.aspect;

import com.epam.spring.theater.dao.DiscountStatisticDao;
import com.epam.spring.theater.model.DiscountStatistic;
import com.epam.spring.theater.model.DiscountType;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Aspect
@Component
public class DiscountAspect {

    @Autowired
    private DiscountStatisticDao discountStatisticDao;


    @Pointcut("within(com.epam.spring.theater.service.impl.DiscountServiceImpl)")
    private void inDiscountService() {
    }

    @Pointcut("execution(* *.getDiscount(..))")
    private void getDiscount() {
    }

    @AfterReturning(pointcut = "getDiscount() && inDiscountService()", returning = "discount")
    private void countDiscountStatistic(JoinPoint jp, Object discount) {
        User user = (User) jp.getArgs()[0];
        Map<Ticket, Map.Entry<DiscountType, BigDecimal>> disc = (Map<Ticket, Map.Entry<DiscountType, BigDecimal>>) discount;
        disc.values().stream().forEach(discountEntry -> {
                    Optional<DiscountStatistic> optionalStatistic = Optional.ofNullable(discountStatisticDao.findByUserIdAndType(user.getUserId(), discountEntry.getKey()));
                    DiscountStatistic discountStatistic = incrementDiscountCount(optionalStatistic.orElse(new DiscountStatistic(discountEntry.getKey(), user.getUserId())));
                    createOrUpdate(discountStatistic);
                }
        );
    }

    private DiscountStatistic incrementDiscountCount(DiscountStatistic discountStatistic) {
        int priceQueryCount = discountStatistic.getAppliedCount() + 1;
        discountStatistic.setAppliedCount(priceQueryCount);
        return discountStatistic;
    }

    private void createOrUpdate(DiscountStatistic discountStatistic) {
        if (discountStatistic.getDiscountStatisticId() == null) {
            discountStatisticDao.create(discountStatistic);
        } else {
            discountStatisticDao.update(discountStatistic);
        }
    }
}

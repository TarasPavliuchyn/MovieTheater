package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.dao.TicketDao;
import com.epam.spring.theater.model.DiscountType;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.service.DiscountService;
import com.epam.spring.theater.util.DiscountStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private List<DiscountStrategy> discountStrategies;

    @Autowired
    private TicketDao ticketDao;

    @Override
    public BigDecimal calculateDiscount(User user, Event event, Date date) {
        Map<DiscountType, BigDecimal> discounts = discountStrategies.stream()
                .collect(Collectors.toMap(this::getDiscountStrategyType, discount -> discount.getDiscount(user, event, date)));
        return discounts.values().stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
    }

    private DiscountType getDiscountStrategyType(DiscountStrategy discountStrategy) {
        DiscountType discountType = null;
        switch (discountStrategy.getClass().getSimpleName()) {
            case "BirthdayDiscountStrategy":
                discountType = DiscountType.BIRTHDAY_DISCOUNT;
                break;
            case "RegularDiscountStrategy":
                discountType = DiscountType.REGULAR_DISCOUNT;
                break;
        }
        return discountType;
    }
}

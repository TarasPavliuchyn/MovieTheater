package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.model.DiscountType;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.service.DiscountService;
import com.epam.spring.theater.util.DiscountStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private List<DiscountStrategy> discountStrategies;
    private Comparator<? super Map.Entry<DiscountType, BigDecimal>> maxDiscountComparator = (
            disc1, disk2) -> disc1.getValue().compareTo(
            disk2.getValue());

    @Override
    public Map<Ticket, Map.Entry<DiscountType, BigDecimal>> getDiscount(User user, Event event, Date date) {
        List<Ticket> actualTickets = getActualTickets(user, event, date);
        return actualTickets
                .stream()
                .collect(Collectors.toMap(ticket -> ticket, discount -> calculateDiscount(user, event, date)));
    }

    private Map.Entry<DiscountType, BigDecimal> calculateDiscount(User user, Event event, Date date) {
        Map<DiscountType, BigDecimal> discounts = discountStrategies
                .stream()
                .collect(Collectors.toMap(discountStrategy -> extractDiscountStrategyType(discountStrategy), discount -> discount.getDiscount(user, event, date)));

        Optional<Map.Entry<DiscountType, BigDecimal>> maxDiscount = discounts.entrySet()
                .stream().max(maxDiscountComparator);
        return maxDiscount.get();
    }

    private DiscountType extractDiscountStrategyType(DiscountStrategy discountStrategy) {
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

    private List<Ticket> getActualTickets(User user, Event event, Date date) {
        return user.getTickets()
                .stream()
                .filter(ticket -> !ticket.getDateTime().before(date) && ticket.getEventId().equals(event.getEventId()))
                .collect(Collectors.toList());
    }
}

package com.epam.spring.theater.service.impl;

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
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private List<DiscountStrategy> discountStrategies;

    @Override
    public Map<Ticket, BigDecimal> getDiscount(User user, Event event, Date date) {
        List<Ticket> actualTickets = getActualTickets(user, event, date);
        return actualTickets
                .stream()
                .collect(Collectors.toMap(ticket -> ticket, discount -> calculateDiscount(user, event, date)));
    }

    private BigDecimal calculateDiscount(User user, Event event, Date date) {
        return discountStrategies
                .stream()
                .map(strategy -> strategy.getDiscount(user, event, date))
                .max(Comparator.naturalOrder()).get();
    }

    private List<Ticket> getActualTickets(User user, Event event, Date date) {
        return user.getTickets()
                .stream()
                .filter(ticket -> !ticket.getDateTime().before(date) && ticket.getEvent().equals(event))
                .collect(Collectors.toList());
    }
}

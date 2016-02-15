package com.epam.spring.theater.util.impl;

import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.util.AbstractDiscountStrategy;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@EqualsAndHashCode
public class RegularDiscountStrategy extends AbstractDiscountStrategy {

    private BigDecimal regularDiscount;

    private Integer viewedMovieForDiscount;

    public BigDecimal getDiscount(User user, Event event, Date date) {
        BigDecimal basePrice = event.getBasePrice();
        BigDecimal calculatedDiscount = getBaseDiscount();
        List<Ticket> purchasedTickets = getPurchasedTicketsWithoutDiscount(user, event, viewedMovieForDiscount);
        long userViewedMovie = purchasedTickets.stream().count();
        if (checkEventExistForDate(event, date) && userViewedMovie >= viewedMovieForDiscount) {
            purchasedTickets.forEach(ticket -> ticket.setDiscounted(true));
            calculatedDiscount = getBaseDiscount().add(regularDiscount);
        }
        return basePrice.multiply(calculatedDiscount);
    }
}

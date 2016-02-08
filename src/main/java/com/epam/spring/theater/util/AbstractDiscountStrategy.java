package com.epam.spring.theater.util;

import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
public abstract class AbstractDiscountStrategy implements DiscountStrategy {
    private SimpleDateFormat dateFormat;
    private BigDecimal baseDiscount = BigDecimal.ZERO;

    protected boolean checkEventExistForDate(Event event, Date birthday) {
        return event.getSchedule().keySet()
                .stream()
                .anyMatch(dateTime -> dateTime.equals(birthday));
    }

    protected boolean isSameDate(Date date1, Date date2) {
        return dateFormat.format(date1).equals(dateFormat.format(date2));
    }

    protected List<Ticket> getPurchasedTicketsWithoutDiscount(User user, Event event, Integer limit) {
        return user.getTickets()
                .stream()
                .filter(ticket -> ticket.isPurchased() && !ticket.isDiscounted() && ticket.getEvent().equals(event))
                .limit(limit)
                .collect(Collectors.toList());
    }
}

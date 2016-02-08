package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.model.*;
import com.epam.spring.theater.service.DiscountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DiscountServiceImplTest extends AbstractTestSuite {

    @Autowired
    private DiscountService discountService;

    @Value("${birthday.discount}")
    private BigDecimal birthdayDiscount;

    @Value("${regular.discount}")
    private BigDecimal regularDiscount;

    @Value("${viewed.movie}")
    private Integer viewedMovie;

    @Test
    public void testGetBirthdayDiscount() throws Exception {
        User user = user = new User.UserBuilder("taras_pavlichyn@epam.com", "qwerty")
                .fullName("Taras Pavliuchyn")
                .birthDay(getFormatter().parse("18/03/1990/00:00")).role(UserRole.CUSTOMER).build();
        Date dateTime = getFormatter().parse("18/03/2016/20:00");
        Event event = createEvent("The Shining", new BigDecimal("100"), Rating.HIGH, getAuditorium("Red"), dateTime);
        Ticket ticket = createTicket(event, dateTime, true);
        user.setTickets(Arrays.asList(ticket));

        Map<Ticket, BigDecimal> ticketDiscounts = discountService.getDiscount(user, event, dateTime);

        BigDecimal actualDiscount = ticketDiscounts.values().stream().findAny().get();
        BigDecimal expectedDiscount = event.getBasePrice().multiply(birthdayDiscount);
        assertFalse(ticketDiscounts.isEmpty());
        assertEquals(expectedDiscount, actualDiscount);
    }

    @Test
    public void testGetRegularDiscount() throws Exception {
        User user = user = new User.UserBuilder("taras_pavlichyn@epam.com", "qwerty")
                .fullName("Taras Pavliuchyn")
                .birthDay(getFormatter().parse("18/03/1990/00:00")).role(UserRole.CUSTOMER).build();
        Date expiredDateTime = getFormatter().parse("17/03/2016/20:00");
        Date dateTime = getFormatter().parse("18/03/2016/20:00");
        Event event = createEvent("The Shining", new BigDecimal("100"), Rating.HIGH, getAuditorium("Red"), dateTime);
        List<Ticket> tickets = new ArrayList<>();
        IntStream.range(0,viewedMovie).forEach(t-> tickets.add(t,createTicket(event, expiredDateTime, true)));
        Ticket justBoughtTicket = createTicket(event, dateTime, true);
        tickets.add(justBoughtTicket);
        user.setTickets(tickets);

        Map<Ticket, BigDecimal> ticketDiscounts = discountService.getDiscount(user, event, dateTime);

        BigDecimal actualDiscount = ticketDiscounts.values().stream().findAny().get();
        BigDecimal expectedDiscount = event.getBasePrice().multiply(regularDiscount);
        assertFalse(ticketDiscounts.isEmpty());
        assertEquals(expectedDiscount, actualDiscount);
    }
}
package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.AbstractTestSuite;
import com.epam.spring.theater.model.DiscountType;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Rating;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.model.UserRole;
import com.epam.spring.theater.service.DiscountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
        User user = new User.UserBuilder("taras_pavlichyn@epam.com", "qwerty")
                .fullName("Taras Pavliuchyn")
                .birthDay(getFormatter().parse("18/03/1990/00:00")).role(UserRole.CUSTOMER).build();
        Date dateTime = getFormatter().parse("18/03/2016/20:00");
        Event event = createEvent("The Shining", new BigDecimal("100"), Rating.HIGH, getAuditorium("Red"), dateTime);
        event.setEventId(1);
        Ticket ticket = createTicket(event, dateTime, true, false);
        user.setTickets(Arrays.asList(ticket));

        Map<Ticket, Map.Entry<DiscountType, BigDecimal>> ticketDiscounts = discountService.getDiscount(user, event, dateTime);
        BigDecimal actualDiscount = ticketDiscounts.values().stream().findAny().get().getValue();
        BigDecimal expectedDiscount = event.getBasePrice().multiply(birthdayDiscount);
        assertFalse(ticketDiscounts.isEmpty());
        assertEquals(expectedDiscount, actualDiscount);
    }

    @Test
    public void testGetRegularDiscount() throws Exception {
        User user = new User.UserBuilder("taras_pavlichyn@epam.com", "qwerty")
                .fullName("Taras Pavliuchyn")
                .birthDay(getFormatter().parse("18/03/1990/00:00")).role(UserRole.CUSTOMER).build();
        Date expiredDateTime = getFormatter().parse("17/03/2016/20:00");
        Date dateTime = getFormatter().parse("18/03/2016/20:00");
        Event event = createEvent("The Shining", new BigDecimal("100"), Rating.HIGH, getAuditorium("Red"), dateTime);
        event.setEventId(1);
        List<Ticket> tickets = new ArrayList<>();
        IntStream.range(0, viewedMovie).forEach(t -> tickets.add(t, createTicket(event, expiredDateTime, true, true)));
        Ticket justBoughtTicket = createTicket(event, dateTime, true, false);
        tickets.add(justBoughtTicket);
        user.setTickets(tickets);

        Map<Ticket, Map.Entry<DiscountType, BigDecimal>> ticketDiscounts = discountService.getDiscount(user, event, dateTime);

        BigDecimal actualDiscount = ticketDiscounts.values().stream().findAny().get().getValue();
        BigDecimal expectedDiscount = event.getBasePrice().multiply(regularDiscount);
        assertFalse(ticketDiscounts.isEmpty());
        assertEquals(expectedDiscount, actualDiscount);
    }
}
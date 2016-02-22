package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.AbstractTestSuite;
import com.epam.spring.theater.dao.TicketDao;
import com.epam.spring.theater.model.DiscountType;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.service.DiscountService;
import com.epam.spring.theater.service.EventService;
import com.epam.spring.theater.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DiscountServiceImplTest extends AbstractTestSuite {

    private static final Integer EXIST_USER_ID = 1;
    private static final String EVENT_NAME = "The Hateful Eight";
    private User user;
    private Event event;
    private Ticket ticket;
    private Date dateTime;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private TicketDao ticketDao;

    @Value("${birthday.discount}")
    private BigDecimal birthdayDiscount;

    @Value("${regular.discount}")
    private BigDecimal regularDiscount;

    @Value("${viewed.movie}")
    private Integer viewedMovie;

    @Before
    public void setUp() throws ParseException {
        user = userService.getById(EXIST_USER_ID);
        event = eventService.getByName(EVENT_NAME);
        Integer ticketId = 2;
        Integer eventId = 1;
        boolean booked = true;
        boolean purchased = false;
        dateTime = getFormatter().parse("18/03/2016");
        ticket = createTicket(ticketId, EXIST_USER_ID, booked, dateTime, eventId, purchased);
    }

    @Test
    public void testGetBirthdayDiscount() throws Exception {
        ticketDao.create(ticket);

        Map<Ticket, Map.Entry<DiscountType, BigDecimal>> ticketDiscounts = discountService.getDiscount(user, event, dateTime);

        BigDecimal actualDiscount = ticketDiscounts.values().stream().findAny().get().getValue();
        BigDecimal expectedDiscount = event.getBasePrice().multiply(birthdayDiscount);
        assertFalse(ticketDiscounts.isEmpty());
        assertEquals(expectedDiscount, actualDiscount);
    }

    @Test
    public void testGetRegularDiscount() throws Exception {
        IntStream.range(0, viewedMovie + 1).forEach(t -> ticketDao.create(ticket));
        Map<Ticket, Map.Entry<DiscountType, BigDecimal>> ticketDiscounts = discountService.getDiscount(user, event, dateTime);
        BigDecimal expectedDiscount = event.getBasePrice().multiply(regularDiscount);
        assertFalse(ticketDiscounts.isEmpty());
        assertEquals(expectedDiscount, expectedDiscount);
    }
}
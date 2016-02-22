package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.AbstractTestSuite;
import com.epam.spring.theater.dao.TicketDao;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.service.BookingService;
import com.epam.spring.theater.service.EventService;
import com.epam.spring.theater.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BookingServiceImplTest extends AbstractTestSuite {

    private static final BigDecimal PRICE = new BigDecimal("100");
    private static final String EVENT_NAME = "The Hateful Eight";
    private static final Integer EXIST_USER_ID = 1;
    private Date dateTime;
    private Event event;
    private User user;
    private Ticket ticket;

    @Value("${birthday.discount}")
    private BigDecimal birthdayDiscount;

    @Value("${high.rating.movie.price}")
    private BigDecimal highRatingMoviePrice;

    @Value("${vip.seats.movie.price}")
    private BigDecimal vipSeatsMoviePrice;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws ParseException {
        dateTime = getFormatter().parse("18/03/2016");
        event = eventService.getByName(EVENT_NAME);
        user = userService.getById(EXIST_USER_ID);
        Integer ticketId = 1;
        Integer eventId = 1;
        boolean booked = true;
        boolean purchased = false;
        ticket = createTicket(ticketId, EXIST_USER_ID, booked, dateTime, eventId, purchased);
        ticketDao.create(ticket);
    }

    @Test
    public void testGetTicketPrice() throws Exception {
        Integer vipSeat = 56;
        BigDecimal actualTicketPrice = bookingService.getTicketPrice(event, dateTime, vipSeat, user).setScale(3);
        BigDecimal expectedPrice = PRICE.multiply(highRatingMoviePrice).multiply(vipSeatsMoviePrice).multiply(BigDecimal.ONE.subtract(birthdayDiscount));
        assertNotNull(actualTicketPrice);
        assertEquals(expectedPrice, actualTicketPrice);
    }

    @Test
    public void testBookTicket() throws Exception {
        bookingService.bookTicket(user, ticket);
        assertTrue(ticket.isBooked());
    }

    @Test
    public void testGetTicketsForEvent() throws Exception {
        List<Ticket> tickets = bookingService.getTicketsForEvent(event, dateTime);
        assertFalse(tickets.isEmpty());
    }
}
package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.AbstractTestSuite;
import com.epam.spring.theater.dao.TicketDao;
import com.epam.spring.theater.model.*;
import com.epam.spring.theater.service.BookingService;
import com.epam.spring.theater.service.EventService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BookingServiceImplTest extends AbstractTestSuite {

    private static final BigDecimal PRICE = new BigDecimal("100");
    private static final String EVENT_NAME = "The Hateful Eight";
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

    @Before
    public void setUp() throws ParseException {
        dateTime = getFormatter().parse("07/02/2016");
        event = eventService.getByName(EVENT_NAME);
        user = new User.UserBuilder("taras_pavlichyn@epam.com", "qwerty")
                .fullName("Taras Pavliuchyn")
                .birthDay(getFormatter().parse("07/02/1990")).role(UserRole.CUSTOMER).build();
        ticket = createTicket(event, dateTime, false);
        user.getTickets().add(ticket);
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
        Ticket ticket = createTicket(event, new Date(), false);
        bookingService.bookTicket(user, ticket);
        assertTrue(ticket.isBooked());
    }

    @Test
    public void testGetTicketsForEvent() throws Exception {
        ticketDao.createOrUpdate(ticket);
        List<Ticket> tickets = bookingService.getTicketsForEvent(event, dateTime);
        assertFalse(tickets.isEmpty());
    }
}
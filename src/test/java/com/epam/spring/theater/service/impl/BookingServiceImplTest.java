package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.dao.TicketDao;
import com.epam.spring.theater.model.*;
import com.epam.spring.theater.service.BookingService;
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
    private Date dateTime;
    private Event event;
    private User user;

    @Value("${birthday.discount}")
    private BigDecimal birthdayDiscount;

    @Value("${high.rating.movie.price}")
    private BigDecimal highRatingMoviePrice;

    @Value("${vip.seats.movie.price}")
    private BigDecimal vipSeatsMoviePrice;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TicketDao ticketDao;

    @Before
    public void setUp() throws ParseException {
        dateTime = getFormatter().parse("18/03/2016/20:00");
        event = createEvent("The Shining", PRICE, Rating.HIGH, getAuditorium("Red"), dateTime);
        user = new User.UserBuilder("taras_pavlichyn@epam.com", "qwerty")
                .fullName("Taras Pavliuchyn")
                .birthDay(getFormatter().parse("18/03/1990/00:00")).role(UserRole.CUSTOMER).build();
        Ticket ticket = createTicket(event, dateTime, false);
        user.getTickets().add(ticket);
    }

    @Test
    public void testGetTicketPrice() throws Exception {
        Integer vipSeat = 56;
        BigDecimal actualTicketPrice = bookingService.getTicketPrice(event, dateTime, vipSeat, user);
        BigDecimal expectedPrice = PRICE.multiply(highRatingMoviePrice).multiply(vipSeatsMoviePrice).multiply(BigDecimal.ONE.subtract(birthdayDiscount));
        assertNotNull(actualTicketPrice);
        assertEquals(expectedPrice, actualTicketPrice);
    }

    @Test
    public void testBookTicket() throws Exception {
        Ticket ticket = createTicket(new Event(), new Date(), false);
        bookingService.bookTicket(user, ticket);
        assertTrue(ticket.isBooked());
    }

    @Test
    public void testGetTicketsForEvent() throws Exception {
        Ticket ticket = createTicket(event, dateTime, false);
        ticketDao.create(ticket);
        List<Ticket> tickets = bookingService.getTicketsForEvent(event, dateTime);
        assertFalse(tickets.isEmpty());
    }
}
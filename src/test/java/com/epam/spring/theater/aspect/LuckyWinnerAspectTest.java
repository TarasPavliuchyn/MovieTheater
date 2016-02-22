package com.epam.spring.theater.aspect;

import com.epam.spring.theater.AbstractTestSuite;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.service.BookingService;
import com.epam.spring.theater.service.EventService;
import com.epam.spring.theater.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class LuckyWinnerAspectTest extends AbstractTestSuite {

    private static final String EVENT_NAME = "The Hateful Eight";
    private static final Integer EXIST_USER_ID = 1;
    private Date dateTime;
    private Event event;
    private User user;
    private Ticket ticket;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private LuckyWinnerAspect luckyWinnerAspect;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;


    @Before
    public void setUp() throws ParseException {
        dateTime = getFormatter().parse("18/03/2016");
        user = userService.getById(EXIST_USER_ID);
        event = eventService.getByName(EVENT_NAME);
        Integer ticketId = 1;
        Integer eventId = 1;
        boolean booked = true;
        boolean purchased = false;
        ticket = createTicket(ticketId, EXIST_USER_ID, booked, dateTime, eventId, purchased);
    }

    @Test
    public void testLuckyWinnerAspect() throws Exception {
        luckyWinnerAspect.setChanceToWin(1);
        bookingService.bookTicket(user, ticket);
        assertTrue(ticket.isBooked());
        assertTrue(ticket.getTicketPrice().signum() == 0);
    }
}
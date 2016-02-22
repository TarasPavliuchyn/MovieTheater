package com.epam.spring.theater.aspect;

import com.epam.spring.theater.AbstractTestSuite;
import com.epam.spring.theater.dao.impl.EventStatisticDaoImpl;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.EventStatistic;
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

import static org.junit.Assert.assertEquals;

public class CounterAspectTest extends AbstractTestSuite {

    private static final String EVENT_NAME = "The Hateful Eight";
    private static final int ACCESS_COUNT = 10;
    private static final int BOOKING_COUNT = 10;
    private static final int PRICE_QUERY_COUNT = 10;
    private static final Integer EXIST_USER_ID = 1;

    private Date dateTime;
    private Event event;
    private User user;
    private Ticket ticket;


    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventStatisticDaoImpl statisticDao;

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
    public void testCountAspectAccessByName() {
        Event event = null;
        for (int i = 0; i < ACCESS_COUNT; i++) {
            event = eventService.getByName(EVENT_NAME);
        }
        EventStatistic eventStatistic = statisticDao.findByEventName(event.getName());
        assertEquals(ACCESS_COUNT + 1, eventStatistic.getAccessedCount());
    }

    @Test
    public void testCountAspectBooking() {
        for (int i = 0; i < BOOKING_COUNT; i++) {
            bookingService.bookTicket(user, ticket);
        }
        EventStatistic eventStatistic = statisticDao.findByEventName(event.getName());
        assertEquals(BOOKING_COUNT, eventStatistic.getBookedCount());
    }

    @Test
    public void testCountAspectPriceQuery() {
        int seat = 2;
        for (int i = 0; i < PRICE_QUERY_COUNT; i++) {
            bookingService.getTicketPrice(event, dateTime, seat, user);
        }
        EventStatistic eventStatistic = statisticDao.findByEventName(event.getName());
        assertEquals(PRICE_QUERY_COUNT, eventStatistic.getPriceQueryCount());
    }
}

package com.epam.spring.theater.aspect;

import com.epam.spring.theater.AbstractTestSuite;
import com.epam.spring.theater.dao.impl.EventStatisticDaoImpl;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.EventStatistic;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.model.UserRole;
import com.epam.spring.theater.service.BookingService;
import com.epam.spring.theater.service.EventService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CounterAspectTest extends AbstractTestSuite {

    private static final String EVENT_NAME = "The Hateful Eight";
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private Date dateTime;
    private static final int ACCESS_COUNT = 10;
    private static final int BOOKING_COUNT = 10;
    private static final int PRICE_QUERY_COUNT = 10;
    private User user;

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventStatisticDaoImpl statisticDao;

    @Before
    public void setUp() throws ParseException {
        dateTime = formatter.parse("07/02/2016/20:00");
        user = new User.UserBuilder("taras_pavlichyn@epam.com", "qwerty")
                .fullName("Taras Pavliuchyn")
                .birthDay(getFormatter().parse("18/03/1990/00:00")).role(UserRole.CUSTOMER).build();
    }

    @Test
    public void testCountAspectAccessByName() {
        Event event = null;
        for (int i = 0; i < ACCESS_COUNT; i++) {
            event = eventService.getByName(EVENT_NAME);
        }
        EventStatistic eventStatistic = statisticDao.findByEventName(event.getName());
        assertEquals(ACCESS_COUNT, eventStatistic.getAccessedCount());
    }

    @Test
    public void testCountAspectBooking() {
        Event event = eventService.getByName(EVENT_NAME);
        for (int i = 0; i < BOOKING_COUNT; i++) {
            bookingService.bookTicket(user, createTicket(event, dateTime, false, false));
        }
        EventStatistic eventStatistic = statisticDao.findByEventName(event.getName());
        assertEquals(BOOKING_COUNT, eventStatistic.getBookedCount());
    }

    @Test
    public void testCountAspectPriceQuery() {
        Event event = eventService.getByName(EVENT_NAME);
        for (int i = 0; i < PRICE_QUERY_COUNT; i++) {
            bookingService.getTicketPrice(event, dateTime, 2, user);
        }
        EventStatistic eventStatistic = statisticDao.findByEventName(event.getName());
        assertEquals(PRICE_QUERY_COUNT, eventStatistic.getPriceQueryCount());
    }
}
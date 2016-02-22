package com.epam.spring.theater.aspect;

import com.epam.spring.theater.AbstractTestSuite;
import com.epam.spring.theater.dao.DiscountStatisticDao;
import com.epam.spring.theater.model.DiscountStatistic;
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

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DiscountAspectTest extends AbstractTestSuite {

    private static final String EVENT_NAME = "The Hateful Eight";
    private static final int APPLIED_DISCOUNT_COUNT = 10;
    private static final Integer EXIST_USER_ID = 1;

    private Date dateTime;
    private Event event;
    private User user;
    private Ticket ticket;

    @Autowired
    private EventService eventService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private DiscountStatisticDao discountStatisticDao;

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
    public void testDiscountAspect() {
        for (int i = 0; i < APPLIED_DISCOUNT_COUNT; i++) {
            discountService.getDiscount(user, event, dateTime);
        }
        DiscountStatistic discountStatistic = discountStatisticDao.findByUserIdAndType(user.getUserId(), DiscountType.BIRTHDAY_DISCOUNT);
        assertEquals(APPLIED_DISCOUNT_COUNT, discountStatistic.getAppliedCount());
    }

}
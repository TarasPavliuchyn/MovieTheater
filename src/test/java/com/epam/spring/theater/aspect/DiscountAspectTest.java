package com.epam.spring.theater.aspect;

import com.epam.spring.theater.AbstractTestSuite;
import com.epam.spring.theater.dao.DiscountStatisticDao;
import com.epam.spring.theater.model.DiscountStatistic;
import com.epam.spring.theater.model.DiscountType;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Rating;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.model.UserRole;
import com.epam.spring.theater.service.BookingService;
import com.epam.spring.theater.service.DiscountService;
import com.epam.spring.theater.service.EventService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DiscountAspectTest extends AbstractTestSuite {

    private static final String EVENT_NAME = "The Hateful Eight";
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private Date dateTime;
    private static final String EVENT_DATE_TIME = "07/02/2016";
    private static final int APPLIED_DISCOUNT_COUNT = 10;
    private Event event;
    private User user;

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private DiscountStatisticDao discountStatisticDao;

    @Before
    public void setUp() throws ParseException {
        dateTime = formatter.parse(EVENT_DATE_TIME);
        event = eventService.getByName(EVENT_NAME);
        user = new User.UserBuilder("taras_pavlichyn@epam.com", "qwerty")
                .fullName("Taras Pavliuchyn")
                .birthDay(getFormatter().parse("07/02/1990")).role(UserRole.CUSTOMER).build();
    }

    @Test
    public void testDiscountAspect() {
        Ticket ticket = createTicket(event, dateTime, true);
        user.setTickets(Arrays.asList(ticket));
        for (int i = 0; i < APPLIED_DISCOUNT_COUNT; i++) {
            discountService.getDiscount(user, event, dateTime);
        }
        DiscountStatistic discountStatistic = discountStatisticDao.findByUserIdAndType(user.getUserId(), DiscountType.BIRTHDAY_DISCOUNT);
        assertEquals(APPLIED_DISCOUNT_COUNT, discountStatistic.getAppliedCount());
    }

}
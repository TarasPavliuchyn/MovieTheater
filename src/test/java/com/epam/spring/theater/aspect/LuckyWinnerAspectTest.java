package com.epam.spring.theater.aspect;

import com.epam.spring.theater.AbstractTestSuite;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Rating;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.model.UserRole;
import com.epam.spring.theater.service.BookingService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class LuckyWinnerAspectTest extends AbstractTestSuite {


    private static final BigDecimal PRICE = new BigDecimal("100");
    private Date dateTime;
    private Event event;
    private User user;
    private Ticket ticket;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private LuckyWinnerAspect luckyWinnerAspect;

    @Before
    public void setUp() throws ParseException {
        dateTime = getFormatter().parse("18/03/2016/20:00");
        event = createEvent("The Shining", PRICE, Rating.HIGH, getAuditorium("Red"), dateTime);
        user = new User.UserBuilder("taras_pavlichyn@epam.com", "qwerty")
                .fullName("Taras Pavliuchyn")
                .birthDay(getFormatter().parse("18/03/1990/00:00")).role(UserRole.CUSTOMER).build();
        ticket = createTicket(event, dateTime, false);
        ticket.setTicketPrice(BigDecimal.TEN);
    }

    @Test
    public void testLuckyWinnerAspect() throws Exception {
        luckyWinnerAspect.setChanceToWin(1);
        bookingService.bookTicket(user, ticket);

        assertTrue(ticket.isBooked());
        assertTrue(ticket.getTicketPrice().signum() == 0);
        assertTrue(!user.getLuckyTickets().isEmpty());
    }
}
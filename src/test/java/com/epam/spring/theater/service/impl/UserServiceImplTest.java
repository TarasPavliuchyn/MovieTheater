package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.AbstractTestSuite;
import com.epam.spring.theater.dao.TicketDao;
import com.epam.spring.theater.dao.impl.UserExistException;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.model.UserRole;
import com.epam.spring.theater.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UserServiceImplTest extends AbstractTestSuite {

    private static final String EXIST_USER_EMAIL = "Taras_Pavliuchyn@epam.com";
    private static final String EXIST_USER_FULL_NAME = "Taras Pavliuchyn";
    private static final Integer EXIST_USER_ID = 1;


    @Autowired
    private UserService userService;

    @Autowired
    private TicketDao ticketDao;

    @Test
    public void testRegister() throws ParseException {
        Date birthday = getFormatter().parse("18/03/1990");
        User user = new User.UserBuilder("TEST@epam.com", "qwerty")
                .fullName("NEW USER")
                .birthDay(birthday).role(UserRole.CUSTOMER).build();
        userService.register(user);
        User registeredUser = userService.getUserByEmail(user.getEmail());
        assertNotNull(registeredUser);

    }

    @Test(expected = UserExistException.class)
    public void testRegisterThrowException() {
        User user = userService.getById(EXIST_USER_ID);
        userService.register(user);
    }

    @Test
    public void testRemove() {
        User user = userService.getById(EXIST_USER_ID);
        userService.remove(user);
        User removedUser = userService.getById(EXIST_USER_ID);
        assertNull(removedUser);
    }

    @Test
    public void testGetById() {
        User userBuId = userService.getById(EXIST_USER_ID);
        assertNotNull(userBuId);
    }

    @Test
    public void testGetUserByEmail() {
        User userByEmail = userService.getUserByEmail(EXIST_USER_EMAIL);
        assertNotNull(userByEmail);
    }

    @Test
    public void testGetUsersByName() {
        User userByName = userService.getUserByName(EXIST_USER_FULL_NAME);
        assertNotNull(userByName);
    }

    @Test
    public void testGetBookedTickets() throws Exception {
        Integer ticketId = 1;
        Integer eventId = 1;
        boolean booked = true;
        boolean purchased = false;
        Date dateTime = getFormatter().parse("18/03/2016");
        Ticket ticket = createTicket(ticketId, EXIST_USER_ID, booked, dateTime, eventId, purchased);
        ticketDao.create(ticket);
        List<Ticket> bookedTickets = userService.getBookedTickets(EXIST_USER_ID);
        assertFalse(bookedTickets.isEmpty());
    }
}
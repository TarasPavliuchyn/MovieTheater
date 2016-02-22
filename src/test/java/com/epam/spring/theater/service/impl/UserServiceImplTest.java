package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.AbstractTestSuite;
import com.epam.spring.theater.dao.impl.UserExistException;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.model.UserRole;
import com.epam.spring.theater.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceImplTest extends AbstractTestSuite {

    private static final String USER_EMAIL = "Taras_Pavliuchyn@epam.com";
    private static final String FULL_NAME = "Taras Pavliuchyn";
    private User user;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy/hh:mm");


    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws ParseException {
        String birthday = "18/03/1990/00:00";
        user = new User.UserBuilder(USER_EMAIL, "qwerty")
                .fullName(FULL_NAME)
                .birthDay(formatter.parse(birthday)).role(UserRole.CUSTOMER).build();
        Ticket ticket = createTicket(new Event(), new Date(), true, true);
        user.setTickets(Arrays.asList(ticket));
    }

    @Test
    public void testRegister() {
        userService.register(user);
        User registeredUser = userService.getUserByEmail(user.getEmail());
        assertNotNull(registeredUser);

    }

    @Test(expected = UserExistException.class)
    public void testRegisterThrowException() {
        userService.register(user);
        userService.register(user);
    }

    @Test
    public void testRemove() {
        userService.register(user);
        userService.remove(user);
        User userById = userService.getById(user.getUserId());
        assertNull(userById);
    }

    @Test
    public void testGetById() {
        userService.register(user);
        User userBuId = userService.getById(user.getUserId());
        assertNotNull(userBuId);
    }

    @Test
    public void testGetUserByEmail() {
        userService.register(user);
        User userByEmail = userService.getUserByEmail(user.getEmail());
        assertNotNull(userByEmail);
    }

    @Test
    public void testGetUsersByName() {
        userService.register(user);
        User userByName = userService.getUserByName(user.getFullName());
        assertNotNull(userByName);
    }

    @Test
    public void testGetBookedTickets() throws Exception {
        userService.register(user);
        List<Ticket> bookedTickets = userService.getBookedTickets(user.getUserId());
        assertFalse(bookedTickets.isEmpty());
    }
}
package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.dao.TicketDao;
import com.epam.spring.theater.dao.UserDao;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TicketDao ticketDao;

    @Override
    public void register(User user) {
        userDao.create(user);
    }

    @Override
    public void remove(User user) {
        userDao.remove(user);
    }

    @Override
    public User getById(Integer userId) {
        return userDao.find(userId);
    }


    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUsersByName(name);
    }

    @Override
    public List<Ticket> getBookedTickets(Integer userId) {
        return ticketDao.getBookedTickets(userId);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll().stream().collect(Collectors.toList());
    }
}

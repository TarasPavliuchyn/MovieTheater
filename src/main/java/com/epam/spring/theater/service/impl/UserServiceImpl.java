package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.dao.UserDao;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void register(User user) {
        Assert.notNull(user.getEmail(), "Email can't be blank");
        Assert.notNull(user.getPassword(), "Password can't be blank");
        userDao.create(user);
    }

    @Override
    public void remove(User user) {
        userDao.remove(user);
    }

    @Override
    public User getById(Integer userId) {
        return userDao.getById(userId);
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
        return userDao.getBookedTickets(userId);
    }
}

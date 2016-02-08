package com.epam.spring.theater.dao;

import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;

import java.util.List;

public interface UserDao {

    User create(User user);

    void remove(User user);

    User getById(Integer userId);

    User getUserByEmail(String email);

    User getUsersByName(String name);

    List<Ticket> getBookedTickets(Integer userId);

    List<User> findAll();
}

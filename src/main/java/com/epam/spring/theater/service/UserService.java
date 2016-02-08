package com.epam.spring.theater.service;

import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;

import java.util.List;

public interface UserService {

    void register(User user);

    void remove(User user);

    User getById(Integer userId);

    User getUserByEmail(String email);

    User getUserByName(String name);

    List<Ticket> getBookedTickets(Integer userId);
}

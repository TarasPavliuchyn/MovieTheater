package com.epam.spring.theater.dao.impl;

import com.epam.spring.theater.dao.CrudDaoImpl;
import com.epam.spring.theater.dao.UserDao;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserDaoImpl extends CrudDaoImpl<User, Integer> implements UserDao {

    @Override
    public User create(User user) {
        Map<Integer, User> users = getStorage();
        if (users.containsValue(user)) {
            throw new UserExistException("User already exists");
        }
        super.createOrUpdate(user.getUserId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        return super.createOrUpdate(user.getUserId(), user);
    }

    @Override
    public User getById(Integer userId) {
        return super.find(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return getStorage().values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().get();
    }

    @Override
    public User getUsersByName(String name) {
        return getStorage().values()
                .stream()
                .filter(user -> user.getFullName().equals(name))
                .findFirst().get();
    }

    @Override
    public List<Ticket> getBookedTickets(Integer userId) {
        return super.find(userId).getTickets()
                .stream()
                .filter(ticket -> ticket.isBooked())
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(super.findAll());
    }
}

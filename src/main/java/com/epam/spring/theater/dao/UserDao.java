package com.epam.spring.theater.dao;

import com.epam.spring.theater.model.User;

public interface UserDao extends CrudDao<User, Integer> {

    User getUserByEmail(String email);

    User getUsersByName(String name);

}

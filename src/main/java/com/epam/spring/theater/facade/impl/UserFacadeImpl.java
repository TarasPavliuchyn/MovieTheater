package com.epam.spring.theater.facade.impl;

import com.epam.spring.theater.converter.impl.UserConverter;
import com.epam.spring.theater.dto.UserDto;
import com.epam.spring.theater.facade.UserFacade;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserService userService;

    @Override
    public void saveList(List<UserDto> userDtos) {
        List<User> events = userConverter.toModelList(userDtos);
        events.forEach(userService::register);
    }

    @Override
    public Collection<UserDto> findAll() {
        List<User> users = userService.findAll();
        Collections.sort(users, Comparator.comparing(User::getEmail));
        return userConverter.toDtoList(users);
    }
}

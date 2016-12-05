package com.epam.spring.theater.converter.impl;

import com.epam.spring.theater.converter.AbstractConverter;
import com.epam.spring.theater.dto.UserDto;
import com.epam.spring.theater.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends AbstractConverter<User, UserDto> {

    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setBirthDay(user.getBirthDay());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        return userDto;
    }

    @Override
    public User convertToModel(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setBirthDay(userDto.getBirthDay());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        return user;
    }
}

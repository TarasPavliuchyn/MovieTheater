package com.epam.spring.theater.facade;

import com.epam.spring.theater.dto.UserDto;

import java.util.Collection;
import java.util.List;

public interface UserFacade {

    void saveList(List<UserDto> userDtos);

    Collection<UserDto> findAll();
}

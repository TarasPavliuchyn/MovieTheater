package com.epam.spring.theater.dto;

import com.epam.spring.theater.model.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class UserDto {
    private Integer userId;
    private String email;
    private String password;
    private String fullName;
    private Date birthDay;
    private UserRole role;
    private Map<Date, Integer> luckyTickets = new HashMap<>();
}

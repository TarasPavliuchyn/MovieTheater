package com.epam.spring.theater.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class User {
    private Integer userId;
    private String email;
    private String password;
    private String fullName;
    private Date birthDay;
    private UserRole role;
    private Map<Date, Integer> luckyTickets = new HashMap<>();
    private List<Ticket> tickets = Collections.emptyList();
}

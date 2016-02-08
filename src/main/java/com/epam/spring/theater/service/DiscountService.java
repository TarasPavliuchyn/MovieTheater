package com.epam.spring.theater.service;

import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DiscountService {

    Map<Ticket, BigDecimal> getDiscount(User user, Event event, Date date);

}

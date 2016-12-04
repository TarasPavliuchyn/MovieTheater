package com.epam.spring.theater.service;

import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.User;

import java.math.BigDecimal;
import java.util.Date;

public interface DiscountService {
    BigDecimal calculateDiscount(User user, Event event, Date date);

}

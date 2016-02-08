package com.epam.spring.theater.util;

import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.User;

import java.math.BigDecimal;
import java.util.Date;

public interface DiscountStrategy {
    BigDecimal getDiscount(User user, Event event, Date date);
}

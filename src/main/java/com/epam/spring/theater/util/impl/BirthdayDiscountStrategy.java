package com.epam.spring.theater.util.impl;

import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.util.AbstractDiscountStrategy;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@EqualsAndHashCode
public class BirthdayDiscountStrategy extends AbstractDiscountStrategy {

    private BigDecimal birthdayDiscount;

    public BigDecimal getDiscount(User user, Event event, Date date) {
        Date customerBirthday = user.getBirthDay();
        BigDecimal basePrice = event.getBasePrice();
        BigDecimal calculatedDiscount = getBaseDiscount();
        if (isSameDate(date, customerBirthday) && checkEventExistForDate(event, date)) {
            calculatedDiscount = getBaseDiscount().add(birthdayDiscount);
        }
        return basePrice.multiply(calculatedDiscount);
    }

}

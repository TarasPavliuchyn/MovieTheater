package com.epam.spring.theater.aspect;

import com.epam.spring.theater.dao.DiscountStatisticDao;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Aspect
@Component
public class LuckyWinnerAspect {

    @Autowired
    private DiscountStatisticDao discountStatisticDao;

    @Setter
    private double chanceToWin;

    @Around("execution(* com.epam.spring.theater.service.BookingService.bookTicket(..)) && args(user,ticket)")
    private void zeroBookIfLuckyUser(ProceedingJoinPoint jp, User user, Ticket ticket) throws Throwable {
        if (checkLucky()) {
            bookWithZeroPrice(user, ticket);
        }
        jp.proceed(new Object[]{user, ticket});
    }

    private void bookWithZeroPrice(User user, Ticket ticket) {
        ticket.setTicketPrice(BigDecimal.ZERO);
        user.getLuckyTickets().put(ticket.getDateTime(), ticket.getEventId());
    }

    private boolean checkLucky() {
        return Math.random() < chanceToWin;
    }
}

package com.epam.spring.theater.aspect;

import com.epam.spring.theater.dao.DiscountStatisticDao;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Aspect
@Component
public class LuckyWinnerAspect {

    @Autowired
    private DiscountStatisticDao discountStatisticDao;

    @Pointcut("within(com.epam.spring.theater.service.BookingService)")
    private void inBookingService() {
    }

    @Pointcut("execution(* *.bookTicket(..)) && args(user,ticket)")
    private void bookTicket() {
    }

    @Around("bookTicket() && inBookingService()")
    private void zeroBookIfLuckyUser(ProceedingJoinPoint jp, User user, Ticket ticket) throws Throwable {
        if (checkLucky()) {
            bookWithZeroPrice(user, ticket);
        }
        jp.proceed(new Object[]{user, ticket});
    }

    private void bookWithZeroPrice(User user, Ticket ticket) {
        ticket.setTicketPrice(BigDecimal.ZERO);
        user.getLuckyTickets().put(ticket.getDateTime(), ticket.getEvent().getName());
    }

    private boolean checkLucky() {
        return Math.random() < 0.95;
    }
}

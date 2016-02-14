package com.epam.spring.theater.aspect;

import com.epam.spring.theater.dao.impl.EventStatisticDaoImpl;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.EventStatistic;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Aspect
@Component
public class CounterAspect {

    @Autowired
    private EventStatisticDaoImpl eventStatisticDao;

    @AfterReturning("execution(* com.epam.spring.theater.service.EventService.getByName(..)) && args(eventName)")
    private void countAccessByName(JoinPoint jp, String eventName) {
        Optional<EventStatistic> optionalStatistic = Optional.ofNullable(eventStatisticDao.find(eventName));
        EventStatistic eventStatistic = incrementAccessCount(optionalStatistic.orElse(new EventStatistic(eventName)));
        eventStatisticDao.createOrUpdate(eventName, eventStatistic);
    }

    @AfterReturning("execution(* com.epam.spring.theater.service.BookingService.bookTicket(..)) && args(user,ticket)")
    private void countBooking(JoinPoint jp, User user, Ticket ticket) {
        Event event = ticket.getEvent();
        Optional<EventStatistic> optionalStatistic = Optional.ofNullable(eventStatisticDao.find(event.getName()));
        EventStatistic eventStatistic = incrementBookingCount(optionalStatistic.orElse(new EventStatistic(event.getName())));
        eventStatisticDao.createOrUpdate(event.getName(), eventStatistic);
    }

    @AfterReturning("execution(* com.epam.spring.theater.service.BookingService.getTicketPrice(..)) && args(event, dateTime, seat, user)")
    private void countPriceQuery(JoinPoint jp, Event event, Date dateTime, Integer seat, User user) {
        Optional<EventStatistic> optionalStatistic = Optional.ofNullable(eventStatisticDao.find(event.getName()));
        EventStatistic eventStatistic = incrementPriceQuery(optionalStatistic.orElse(new EventStatistic(event.getName())));
        eventStatisticDao.createOrUpdate(event.getName(), eventStatistic);
    }

    private EventStatistic incrementAccessCount(EventStatistic eventStatistic) {
        int accessedCount = eventStatistic.getAccessedCount() + 1;
        eventStatistic.setAccessedCount(accessedCount);
        return eventStatistic;
    }

    private EventStatistic incrementBookingCount(EventStatistic eventStatistic) {
        int bookingCount = eventStatistic.getBookedCount() + 1;
        eventStatistic.setBookedCount(bookingCount);
        return eventStatistic;
    }

    private EventStatistic incrementPriceQuery(EventStatistic eventStatistic) {
        int priceQueryCount = eventStatistic.getPriceQueryCount() + 1;
        eventStatistic.setPriceQueryCount(priceQueryCount);
        return eventStatistic;
    }
}
package com.epam.spring.theater.aspect;

import com.epam.spring.theater.dao.impl.EventDaoImpl;
import com.epam.spring.theater.dao.impl.EventStatisticDaoImpl;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.EventStatistic;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
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

    @Autowired
    private EventDaoImpl eventDao;


    @AfterReturning(pointcut = "execution(* com.epam.spring.theater.service.EventService.getByName(..))", returning = "event")
    private void countAccessByName(Event event) {
        if (event != null) {
            Optional<EventStatistic> optionalStatistic = Optional.ofNullable(eventStatisticDao.findByEventName(event.getName()));
            EventStatistic eventStatistic = incrementAccessCount(optionalStatistic.orElse(new EventStatistic(event.getName())));
            createOrUpdate(eventStatistic);
        }
    }

    @AfterReturning("execution(* com.epam.spring.theater.service.BookingService.bookTicket(..)) && args(user,ticket)")
    private void countBooking(User user, Ticket ticket) {
        Event event = eventDao.find(ticket.getEventId());
        Optional<EventStatistic> optionalStatistic = Optional.ofNullable(eventStatisticDao.findByEventName(event.getName()));
        EventStatistic eventStatistic = incrementBookingCount(optionalStatistic.orElse(new EventStatistic(event.getName())));
        createOrUpdate(eventStatistic);
    }

    @AfterReturning("execution(* com.epam.spring.theater.service.BookingService.discountTicketPrice(..)) && args(event, dateTime, seat, user)")
    private void countPriceQuery(Event event, Date dateTime, Integer seat, User user) {
        Optional<EventStatistic> optionalStatistic = Optional.ofNullable(eventStatisticDao.findByEventName(event.getName()));
        EventStatistic eventStatistic = incrementPriceQuery(optionalStatistic.orElse(new EventStatistic(event.getName())));
        createOrUpdate(eventStatistic);
    }

    private EventStatistic incrementAccessCount(EventStatistic eventStatistic) {
        int accessedCount = eventStatistic.getAccessedCount() + 1;
        eventStatistic.setAccessedCount(accessedCount);
        return eventStatistic;
    }

    private void createOrUpdate(EventStatistic eventStatistic) {
        if (eventStatistic.getEventStatisticId() == null) {
            eventStatisticDao.create(eventStatistic);
        } else {
            eventStatisticDao.update(eventStatistic);
        }
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

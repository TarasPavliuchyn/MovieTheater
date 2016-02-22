package com.epam.spring.theater.service;

import com.epam.spring.theater.model.Auditorium;
import com.epam.spring.theater.model.Event;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface EventService {

    Event create(Event event);

    void remove(Event event);

    Event getByName(String name);

    Collection<Event> findAll();

    Collection<Event> getForDateRange(Date fromDate, Date toDate);

    Collection<Event> getNextEvents(Date toDate);

    void assignAuditorium(Event event, Auditorium auditorium, Date date);

}

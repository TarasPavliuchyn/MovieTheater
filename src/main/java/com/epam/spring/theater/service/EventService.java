package com.epam.spring.theater.service;

import com.epam.spring.theater.model.Auditorium;
import com.epam.spring.theater.model.Event;

import java.util.Date;
import java.util.List;

public interface EventService {

    Event create(Event event);

    void remove(Event event);

    Event getByName(String name);

    List<Event> getAll();

    List<Event> getForDateRange(Date fromDate, Date toDate);

    List<Event> getNextEvents(Date toDate);

    void assignAuditorium(String eventName, Auditorium auditorium, Date date);

}

package com.epam.spring.theater.dao;

import com.epam.spring.theater.model.Auditorium;
import com.epam.spring.theater.model.Event;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface EventDao {

    Event create(Event event);

    void remove(Event event);

    Event getByName(String name);

    List<Event> getAll();

    List<Event> getForDateRange(Date fromDate, Date toDate);

    List<Event> getNextEvents(Date toDate);

    void assignAuditorium(String eventName, Auditorium auditorium, Date date);

}

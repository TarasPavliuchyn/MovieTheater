package com.epam.spring.theater.dao;

import com.epam.spring.theater.model.Auditorium;
import com.epam.spring.theater.model.Event;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface EventDao extends CrudDao<Event, String> {

    Event getByName(String name);

    Collection<Event> getForDateRange(Date fromDate, Date toDate);

    Collection<Event> getNextEvents(Date toDate);

    void assignAuditorium(Event event, Auditorium auditorium, Date date);

}

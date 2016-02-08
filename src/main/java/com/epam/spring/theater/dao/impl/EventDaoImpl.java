package com.epam.spring.theater.dao.impl;

import com.epam.spring.theater.dao.EventDao;
import com.epam.spring.theater.dao.GenericDaoImpl;
import com.epam.spring.theater.model.Auditorium;
import com.epam.spring.theater.model.Event;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EventDaoImpl extends GenericDaoImpl<Event, String> implements EventDao {

    @Override
    public Event create(Event event) {
        return super.create(event.getName(), event);
    }

    @Override
    public void remove(Event event) {
        super.remove(event);
    }

    @Override
    public Event getByName(String name) {
        return super.find(name);
    }

    @Override
    public List<Event> getAll() {
        return new ArrayList<>(super.findAll());
    }

    @Override
    public List<Event> getForDateRange(Date fromDate, Date toDate) {
        return getEventForDateRange(fromDate, toDate);
    }

    @Override
    public List<Event> getNextEvents(Date toDate) {
        return getEventForDateRange(new Date(), toDate);
    }

    @Override
    public void assignAuditorium(String eventName, Auditorium auditorium, Date date) {
        Event event = super.find(eventName);
        event.getSchedule().put(date, auditorium);
    }

    private List<Event> getEventForDateRange(Date fromDate, Date toDate) {
        final Range<Date> range = Range.between(fromDate, toDate);
        return super.findAll().stream()
                .filter(event -> event.getSchedule().keySet().stream().anyMatch(date -> range.contains(date)))
                .collect(Collectors.toList());
    }

}

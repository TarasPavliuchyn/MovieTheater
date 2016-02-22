package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.dao.EventDao;
import com.epam.spring.theater.model.Auditorium;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

    @Override
    public Event create(Event event) {
        return eventDao.create(event);
    }

    @Override
    public void remove(Event event) {
        eventDao.remove(event);
    }

    @Override
    public Event getByName(String name) {
        return eventDao.getByName(name);
    }

    @Override
    public Collection<Event> findAll() {
        return eventDao.findAll();
    }

    @Override
    public Collection<Event> getForDateRange(Date fromDate, Date toDate) {
        return eventDao.getForDateRange(fromDate, toDate);
    }

    @Override
    public Collection<Event> getNextEvents(Date toDate) {
        return eventDao.getNextEvents(toDate);
    }

    @Override
    public void assignAuditorium(Event event, Auditorium auditorium, Date date) {
        eventDao.assignAuditorium(event, auditorium, date);
    }
}

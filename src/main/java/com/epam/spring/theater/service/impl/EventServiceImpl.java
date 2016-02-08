package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.dao.EventDao;
import com.epam.spring.theater.model.Auditorium;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Event> getAll() {
        return eventDao.getAll();
    }

    @Override
    public List<Event> getForDateRange(Date fromDate, Date toDate) {
        return eventDao.getForDateRange(fromDate, toDate);
    }

    @Override
    public List<Event> getNextEvents(Date toDate) {
        return eventDao.getNextEvents(toDate);
    }

    @Override
    public void assignAuditorium(String eventName, Auditorium auditorium, Date date) {
        eventDao.assignAuditorium(eventName, auditorium, date);
    }
}

package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.AbstractTestSuite;
import com.epam.spring.theater.model.Auditorium;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Rating;
import com.epam.spring.theater.service.EventService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class EventServiceImplTest extends AbstractTestSuite {

    private static final String EVENT_NAME = "The Hateful Eight";
    private static final String OTHER_MOVIE = "The Shining";
    private static final String GREEN = "Green";
    private static final String RED = "Red";
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy/hh:mm");

    @Autowired
    private EventService eventService;

    @Test
    public void testCreate() throws ParseException {
        Date dateTime = formatter.parse("07/02/2016/20:00");
        Event event = createEvent(OTHER_MOVIE, new BigDecimal("100"), Rating.HIGH, getAuditorium(RED), dateTime);
        eventService.create(event);
        Event actualEvent = eventService.getByName(OTHER_MOVIE);
        assertNotNull(actualEvent);
    }

    @Test
    public void testRemove() {
        Event event = eventService.getByName(EVENT_NAME);
        eventService.remove(event);
        assertNull(eventService.getByName(EVENT_NAME));
    }

    @Test
    public void testGetByName() {
        assertNotNull(eventService.getByName(EVENT_NAME));
    }

    @Test
    public void testGetAll() {
        assertFalse(eventService.findAll().isEmpty());
    }

    @Test
    public void testGetForDateRange() throws ParseException {
        String eventDateTimeFrom = "07/02/2016/16:00";
        String eventDateTimeTo = "07/02/2017/22:00";
        Collection<Event> events = eventService.getForDateRange(formatter.parse(eventDateTimeFrom), formatter.parse(eventDateTimeTo));
        assertFalse(events.isEmpty());
    }

    @Test
    public void testGetNextEvents() throws ParseException {
        String eventDateTimeTo = "07/04/2016/22:00";
        Collection<Event> events = eventService.getNextEvents(formatter.parse(eventDateTimeTo));
        assertFalse(events.isEmpty());
    }

    @Test
    public void testAssignAuditorium() {
        Event event = createEvent(OTHER_MOVIE, new BigDecimal("80"), Rating.HIGH, null, null);
        eventService.create(event);
        Auditorium auditorium = getAuditorium(GREEN);
        eventService.assignAuditorium(event, auditorium, new Date());
        Event actualEvent = eventService.getByName(OTHER_MOVIE);
        assertFalse(actualEvent.getSchedule().isEmpty());
    }

}
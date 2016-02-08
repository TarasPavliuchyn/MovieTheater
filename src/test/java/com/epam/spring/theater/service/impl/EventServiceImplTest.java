package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.model.Auditorium;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Rating;
import com.epam.spring.theater.service.EventService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class EventServiceImplTest extends AbstractTestSuite {

    private static final String EVENT_NAME = "The Hateful Eight";
    private static final String OTHER_MOVIE = "The Shining";
    private static final String GREEN = "Green";
    private static final String RED = "Red";
    private Event event;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy/hh:mm");

    @Autowired
    private EventService eventService;

    @Autowired
    private AuditoriumServiceImpl auditoriumService;


    @Before
    public void setUp() throws ParseException {
        String eventDateTime = "07/02/2016/20:00";
        Date dateTime = formatter.parse(eventDateTime);
        event = createEvent(EVENT_NAME, new BigDecimal("100"), Rating.HIGH, getAuditorium(RED), dateTime);
    }

    @Test
    public void testCreate() {
        eventService.create(event);
        Event actualEvent = eventService.getByName(EVENT_NAME);
        assertNotNull(actualEvent);
    }

    @Test
    public void testRemove() {
        eventService.create(event);
        eventService.remove(event);
        assertNull(eventService.getByName(EVENT_NAME));
    }

    @Test
    public void testGetByName() {
        eventService.create(event);
        assertNotNull(eventService.getByName(EVENT_NAME));
    }

    @Test
    public void testGetAll() {
        eventService.create(event);
        assertFalse(eventService.getAll().isEmpty());
    }

    @Test
    public void testGetForDateRange() throws ParseException {
        String eventDateTimeFrom = "07/02/2016/16:00";
        String eventDateTimeTo = "07/02/2016/22:00";
        eventService.create(event);
        List<Event> events = eventService.getForDateRange(formatter.parse(eventDateTimeFrom), formatter.parse(eventDateTimeTo));
        assertFalse(events.isEmpty());
    }

    @Test
    public void testGetNextEvents() throws ParseException {
        String eventDateTimeTo = "07/02/2016/22:00";
        eventService.create(event);
        List<Event> events = eventService.getNextEvents(formatter.parse(eventDateTimeTo));
        assertFalse(events.isEmpty());
    }

    @Test
    public void testAssignAuditorium() {
        eventService.create(createEvent(OTHER_MOVIE, new BigDecimal("80"), Rating.HIGH, null, null));
        Auditorium auditorium = getAuditorium(GREEN);
        eventService.assignAuditorium(OTHER_MOVIE, auditorium, new Date());
        Event actualEvent = eventService.getByName(OTHER_MOVIE);
        assertFalse(actualEvent.getSchedule().isEmpty());
    }

}
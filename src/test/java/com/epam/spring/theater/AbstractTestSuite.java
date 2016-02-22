package com.epam.spring.theater;

import com.epam.spring.theater.model.Auditorium;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Rating;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.service.AuditoriumService;
import lombok.Getter;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Getter
public abstract class AbstractTestSuite {

    @Autowired
    private AuditoriumService auditoriumService;

    @Value("${auditorium.red.name}")
    private String auditoriumName;

    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    protected Event createEvent(String name, BigDecimal price, Rating rating, Auditorium redAuditorium, Date date) {
        Event event = new Event();
        event.setBasePrice(price);
        event.setName(name);
        event.setRating(rating);
        event.setSchedule(new HashMap<Date, String>() {
            {
                put(date, auditoriumName);
            }
        });
        return event;
    }

    protected Auditorium getAuditorium(String name) {
        return auditoriumService.getAuditoriums()
                .stream()
                .filter(auditorium -> auditorium.getName().equals(name))
                .findFirst().get();
    }

    protected Ticket createTicket(Event event, Date dateTime, boolean purchased) {
        Ticket ticket = new Ticket();
        ticket.setBooked(true);
        ticket.setDateTime(dateTime);
        ticket.setEvent(event);
        ticket.setPurchased(purchased);
        return ticket;
    }
}

package com.epam.spring.theater.service;

import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface BookingService {

    BigDecimal getTicketPrice(Event event, Date dateTime, Integer seat, User user);

    void bookTicket(User user, Ticket ticket);

    List<Ticket> getTicketsForEvent(Event event, Date date);
}

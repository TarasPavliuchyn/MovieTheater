package com.epam.spring.theater.service;

import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface BookingService {

    BigDecimal getTicketPrice(String eventName, Date dateTime, Integer seat, Integer userId);

    void bookTicket(User user, Ticket ticket);

    List<Ticket> getTicketsForEvent(Event event, Date date);
}

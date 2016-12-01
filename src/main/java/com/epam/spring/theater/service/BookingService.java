package com.epam.spring.theater.service;

import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface BookingService {

    BigDecimal getTicketPrice(Integer eventId, Date dateTime, Integer seat, Integer userId);

    void bookTicket(User user, Ticket ticket);

    List<Ticket> getTicketsForEvent(String event, Date date);

    Ticket getTicketById(Integer ticketId);
}

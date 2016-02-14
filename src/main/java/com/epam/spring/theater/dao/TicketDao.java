package com.epam.spring.theater.dao;

import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Ticket;

import java.util.Date;
import java.util.List;

public interface TicketDao {
    List<Ticket> getPurchasedTickets(Event event, Date date);

    void createOrUpdate(Ticket ticket);
}

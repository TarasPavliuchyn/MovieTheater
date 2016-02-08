package com.epam.spring.theater.dao.impl;

import com.epam.spring.theater.dao.GenericDaoImpl;
import com.epam.spring.theater.dao.TicketDao;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TicketDaoImpl extends GenericDaoImpl<Ticket, Integer> implements TicketDao {

    @Override
    public List<Ticket> getPurchasedTickets(Event event, Date date) {
        return super.findAll()
                .stream()
                .filter(ticket -> ticket.getEvent().equals(event) && ticket.getDateTime().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public void create(Ticket ticket) {
        super.create(ticket.getTicketId(), ticket);
    }
}

package com.epam.spring.theater.converter.impl;

import com.epam.spring.theater.converter.AbstractConverter;
import com.epam.spring.theater.dao.EventDao;
import com.epam.spring.theater.dto.EventDto;
import com.epam.spring.theater.dto.TicketDto;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Optional.ofNullable;

@Component
public class TicketConverter extends AbstractConverter<Ticket, TicketDto> {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private EventConverter eventConverter;

    @Override
    public TicketDto convertToDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setBooked(ticket.isBooked());
        ticketDto.setDateTime(ticket.getDateTime());
        ticketDto.setDiscounted(ticket.isDiscounted());
        Event event = eventDao.find(ticket.getEventId());
        ofNullable(event)
                .map(eventConverter::convertToDto)
                .ifPresent(ticketDto::setEvent);
        ofNullable(event)
                .map(Event::getSchedule)
                .map(schedule -> schedule.get(ticket.getDateTime()))
                .ifPresent(ticketDto::setAuditoriumName);
        ticketDto.setPurchased(ticket.isPurchased());
        ticketDto.setTicketId(ticket.getTicketId());
        ticketDto.setTicketPrice(ticket.getTicketPrice());
        ticketDto.setUserId(ticket.getUserId());
        ticketDto.setSeat(ticket.getSeat());
        return ticketDto;
    }

    @Override
    public Ticket convertToModel(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setBooked(ticketDto.isBooked());
        ticket.setDateTime(ticketDto.getDateTime());
        ticket.setDiscounted(ticketDto.isDiscounted());
        ofNullable(ticketDto.getEvent())
                .map(EventDto::getName)
                .map(eventDao::getByName)
                .map(event -> event.getEventId())
                .ifPresent(ticket::setEventId);
        ticket.setPurchased(ticketDto.isPurchased());
        ticket.setTicketId(ticketDto.getTicketId());
        ticket.setTicketPrice(ticketDto.getTicketPrice());
        ticket.setUserId(ticketDto.getUserId());
        ticket.setSeat(ticketDto.getSeat());
        return ticket;
    }
}

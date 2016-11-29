package com.epam.spring.theater.converter.impl;

import com.epam.spring.theater.converter.AbstractConverter;
import com.epam.spring.theater.dto.TicketDto;
import com.epam.spring.theater.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketConverter extends AbstractConverter<Ticket, TicketDto> {

    @Override
    public TicketDto convertToDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setBooked(ticket.isBooked());
        ticketDto.setDateTime(ticket.getDateTime());
        ticketDto.setDiscounted(ticket.isDiscounted());
        ticketDto.setEventId(ticket.getEventId());
        ticketDto.setPurchased(ticket.isPurchased());
        ticketDto.setTicketId(ticket.getTicketId());
        ticketDto.setTicketPrice(ticket.getTicketPrice());
        ticketDto.setUserId(ticket.getUserId());
        return ticketDto;
    }

    @Override
    public Ticket convertToModel(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setBooked(ticketDto.isBooked());
        ticket.setDateTime(ticketDto.getDateTime());
        ticket.setDiscounted(ticketDto.isDiscounted());
        ticket.setEventId(ticketDto.getEventId());
        ticket.setPurchased(ticketDto.isPurchased());
        ticket.setTicketId(ticketDto.getTicketId());
        ticket.setTicketPrice(ticketDto.getTicketPrice());
        ticket.setUserId(ticketDto.getUserId());
        return ticket;
    }
}

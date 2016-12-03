package com.epam.spring.theater.facade;

import com.epam.spring.theater.dto.TicketDto;

import java.util.Date;
import java.util.List;

public interface BookingFacade {

    TicketDto discountTicketPrice(String userEmail, Integer ticketId);

    TicketDto bookTicket(String userEmail, Integer ticketId);

    List<TicketDto> getTicketsForEvent(String eventName, Date date);

    TicketDto getTicketById(Integer ticketId);

    List<TicketDto> getBookedTickets(String userEmail);
}

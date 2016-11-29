package com.epam.spring.theater.facade;

import com.epam.spring.theater.dto.TicketDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface BookingFacade {

    BigDecimal getTicketPrice(String eventName, Date dateTime, Integer seat, Integer userId);

    void bookTicket(Integer userId, Integer ticketId);

    List<TicketDto> getTicketsForEvent(String eventName, Date date);

}

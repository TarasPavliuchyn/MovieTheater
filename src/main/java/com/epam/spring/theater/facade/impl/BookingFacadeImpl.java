package com.epam.spring.theater.facade.impl;

import com.epam.spring.theater.converter.impl.TicketConverter;
import com.epam.spring.theater.dto.TicketDto;
import com.epam.spring.theater.facade.BookingFacade;
import com.epam.spring.theater.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class BookingFacadeImpl implements BookingFacade {

    @Autowired
    private TicketConverter ticketConverter;

    @Autowired
    private BookingService bookingService;

    @Override
    public BigDecimal getTicketPrice(String eventName, Date dateTime, Integer seat, Integer userId) {
        return bookingService.getTicketPrice(eventName, dateTime, seat, userId);
    }

    @Override
    public void bookTicket(Integer userId, Integer ticketId) {

    }

    @Override
    public List<TicketDto> getTicketsForEvent(String eventName, Date date) {
        return null;
    }
}

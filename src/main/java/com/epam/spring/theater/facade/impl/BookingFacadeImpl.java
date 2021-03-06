package com.epam.spring.theater.facade.impl;

import com.epam.spring.theater.converter.impl.TicketConverter;
import com.epam.spring.theater.dao.TicketDao;
import com.epam.spring.theater.dao.impl.UserNotFoundException;
import com.epam.spring.theater.dto.TicketDto;
import com.epam.spring.theater.facade.BookingFacade;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.service.BookingService;
import com.epam.spring.theater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;

@Component
public class BookingFacadeImpl implements BookingFacade {

    @Autowired
    private TicketConverter ticketConverter;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketDao ticketDao;

    @Override
    public TicketDto discountTicketPrice(String userEmail, Integer ticketId) {
        Ticket ticket = bookingService.getTicketById(ticketId);
        Integer userId = null;
        try {
            userId = userService.getUserByEmail(userEmail).getUserId();
        } catch (EmptyResultDataAccessException ex) {
            throw new UserNotFoundException(format("User '%s' is not found", userEmail), ex);
        }
        BigDecimal discount = bookingService.calculateTicketDiscount(ticket.getEventId(), ticket.getDateTime(), ticket.getSeat(), userId);
        BigDecimal priceWithDiscount = ticket.getTicketPrice().subtract(discount).setScale(2, BigDecimal.ROUND_HALF_UP);
        ticket.setTicketPrice(priceWithDiscount);
        ticket.setDiscounted(true);
        return ticketConverter.convertToDto(ticketDao.update(ticket));
    }

    @Override
    public TicketDto bookTicket(String userEmail, Integer ticketId) {
        Ticket ticket = bookingService.getTicketById(ticketId);
        User user = userService.getUserByEmail(userEmail);
        bookingService.bookTicket(user, ticket);
        return ticketConverter.convertToDto(ticket);
    }

    @Override
    public List<TicketDto> getTicketsForEvent(String eventName, Date date) {
        List<Ticket> tickets = bookingService.getTicketsForEvent(eventName, date);
        return ticketConverter.toDtoList(tickets);
    }

    @Override
    public TicketDto getTicketById(Integer ticketId) {
        Ticket ticket = bookingService.getTicketById(ticketId);
        return ticketConverter.convertToDto(ticket);
    }

    @Override
    public List<TicketDto> getBookedTickets(String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        List<Ticket> tickets = userService.getBookedTickets(user.getUserId());
        return ticketConverter.toDtoList(tickets);
    }
}

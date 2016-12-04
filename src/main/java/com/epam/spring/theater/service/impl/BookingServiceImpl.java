package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.dao.EventDao;
import com.epam.spring.theater.dao.TicketDao;
import com.epam.spring.theater.dao.UserDao;
import com.epam.spring.theater.model.*;
import com.epam.spring.theater.service.AuditoriumService;
import com.epam.spring.theater.service.BookingService;
import com.epam.spring.theater.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Value("${high.rating.movie.price}")
    private BigDecimal highRatingMoviePrice;

    @Value("${vip.seats.movie.price}")
    private BigDecimal vipSeatsMoviePrice;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private AuditoriumService auditoriumService;

    @Override
    public BigDecimal calculateTicketDiscount(Integer eventId, Date dateTime, Integer seat, Integer userId) {
        Event event = eventDao.find(eventId);
        User user = userDao.find(userId);
        BigDecimal discount = discountService.calculateDiscount(user, event, dateTime);
        return discount;
    }

    @Override
    public void bookTicket(User user, Ticket ticket) {
        ticket.setBooked(true);
        ticket.setUserId(user.getUserId());
        ticketDao.update(ticket);
        userDao.update(user);
    }

    @Override
    public List<Ticket> getTicketsForEvent(String eventName, Date date) {
        List<Ticket> tickets = ticketDao.getPurchasedTickets(eventName, date);
        tickets.forEach(ticket -> calculatePrice(date, ticket));
        return tickets;
    }

    @Override
    public Ticket getTicketById(Integer ticketId) {
        return ticketDao.find(ticketId);
    }

    private void calculatePrice(Date date, Ticket ticket) {
        Event event = eventDao.find(ticket.getEventId());
        if (event != null) {
            BigDecimal ticketPrice = event.getBasePrice();
            if (event.getRating() == Rating.HIGH) {
                ticketPrice = ticketPrice.multiply(highRatingMoviePrice);
            }
            ticketPrice = calculatePriceForVIPSeat(date, ticket.getSeat(), ticketPrice, event);
            ticket.setTicketPrice(ticketPrice);
            ticketDao.update(ticket);
        }
    }

    private BigDecimal calculatePriceForVIPSeat(Date dateTime, Integer seat, BigDecimal basePrice, Event event) {
        String auditoriumName = event.getSchedule().get(dateTime);
        Auditorium auditorium = auditoriumService.findByName(auditoriumName);
        if (auditorium.getVipSeats().contains(seat)) {
            basePrice = basePrice.multiply(vipSeatsMoviePrice);
        }
        return basePrice;
    }
}

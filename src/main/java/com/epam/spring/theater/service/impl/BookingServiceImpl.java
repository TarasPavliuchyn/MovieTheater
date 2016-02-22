package com.epam.spring.theater.service.impl;

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
import java.util.Map;

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
    private AuditoriumService auditoriumService;

    @Override
    public BigDecimal getTicketPrice(Event event, Date dateTime, Integer seat, User user) {
        BigDecimal basePrice = event.getBasePrice();
        if (event.getRating() == Rating.HIGH) {
            basePrice = basePrice.multiply(highRatingMoviePrice);
        }
        String auditoriumName = event.getSchedule().get(dateTime);
        Auditorium auditorium = auditoriumService.findByName(auditoriumName);
        if (auditorium.getVipSeats().contains(seat)) {
            basePrice = basePrice.multiply(vipSeatsMoviePrice);
        }
        event.setBasePrice(basePrice);
        BigDecimal calculatedDiscount = calculateDiscount(user, event, dateTime);
        return basePrice.subtract(calculatedDiscount);
    }

    @Override
    public void bookTicket(User user, Ticket ticket) {
        ticket.setBooked(true);
        ticketDao.update(ticket);
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            user.getTickets().add(ticket);
            userDao.update(user);
        }
    }

    @Override
    public List<Ticket> getTicketsForEvent(Event event, Date date) {
        return ticketDao.getPurchasedTickets(event, date);
    }

    private BigDecimal calculateDiscount(User user, Event event, Date dateTime) {
        Map<Ticket, Map.Entry<DiscountType, BigDecimal>> discounts = discountService.getDiscount(user, event, dateTime);
        return discounts.values().stream().findFirst().map(discount -> discount.getValue()).orElse(BigDecimal.ZERO);
    }
}

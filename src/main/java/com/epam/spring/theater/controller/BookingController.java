package com.epam.spring.theater.controller;

import com.epam.spring.theater.dto.TicketDto;
import com.epam.spring.theater.facade.BookingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingFacade bookingFacade;

    @RequestMapping(value = "/price", method = RequestMethod.GET)
    public ModelAndView calculateTicketPrice(@RequestParam("userId") Integer userId,
                                             @RequestParam("eventName") String eventName,
                                             @RequestParam("seat") Integer seatNumber,
                                             @DateTimeFormat(pattern = "yyyy-MM-dd") Date eventDate) {

        BigDecimal ticketPrice = bookingFacade.getTicketPrice(eventName, eventDate, seatNumber, userId);
        ModelAndView mav = new ModelAndView("booking");
        mav.addObject("ticketPrice", ticketPrice);
        return mav;
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.GET)
    public String bookTicket(@RequestParam("userId") Integer userId,
                             @RequestParam("ticketId") Integer ticketId,
                             Model model) {

        bookingFacade.bookTicket(userId, ticketId);
        model.addAttribute("booked", true);
        return "redirect:booking";
    }

    @RequestMapping(value = "/ticket", method = RequestMethod.GET)
    public String retrieveTicketsForEvent(@RequestParam("eventName") String eventName,
                                          @DateTimeFormat(pattern = "yyyy-MM-dd") Date eventDate) {

        List<TicketDto> tickets = bookingFacade.getTicketsForEvent(eventName, eventDate);
        ModelAndView mav = new ModelAndView("booking");
        mav.addObject("tickets", tickets);
        return "redirect:booking";
    }

}
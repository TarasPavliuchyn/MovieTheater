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

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingFacade bookingFacade;

    @RequestMapping(value = "/discount", method = RequestMethod.POST)
    public String calculateTicketPrice(@RequestParam("userEmail") String userEmail,
                                       @RequestParam("ticketId") Integer ticketId,
                                       Model model) {
        TicketDto ticket = bookingFacade.discountTicketPrice(userEmail, ticketId);
        model.addAttribute("ticket", ticket);
        model.addAttribute("userEmail", userEmail);
        return "booking";
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public String bookTicket(@RequestParam("userEmail") String userEmail,
                             @RequestParam("ticketId") Integer ticketId,
                             Model model) {
        TicketDto ticket = bookingFacade.bookTicket(userEmail, ticketId);
        model.addAttribute("ticket", ticket);
        model.addAttribute("userEmail", userEmail);
        return "booking";
    }

    @RequestMapping(value = "/ticket", method = RequestMethod.GET)
    public String ticketDetails(@RequestParam("ticketId") Integer ticketId, Model model) {
        TicketDto ticket = bookingFacade.getTicketById(ticketId);
        model.addAttribute("ticket", ticket);
        return "booking";
    }

    @RequestMapping(value = "/ticket/pdf", method = RequestMethod.GET)
    public ModelAndView getBookedTickets(HttpServletResponse response, String userEmail) {
        List<TicketDto> tickets = bookingFacade.getBookedTickets(userEmail);
        ModelAndView mav = new ModelAndView("pdfView", "tickets", tickets);
        mav.addObject("userEmail", userEmail);
        response.setContentType("application/pdf");
        return mav;
    }

    @RequestMapping(value = "/tickets", method = RequestMethod.GET)
    public String retrieveTicketsForEvent(@RequestParam("eventName") String eventName,
                                          @DateTimeFormat(pattern = "yyyy-MM-dd") Date eventDate,
                                          Model model) {
        List<TicketDto> tickets = bookingFacade.getTicketsForEvent(eventName, eventDate);
        model.addAttribute("tickets", tickets);
        model.addAttribute("eventName", eventName);
        model.addAttribute("eventDate", eventDate);
        return "ticketList";
    }

}
package com.epam.spring.theater.controller;

import com.epam.spring.theater.facade.EventFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EventController {

    @Autowired
    private EventFacade eventFacade;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String getAllEvents(ModelMap model) {
        model.addAttribute("events", eventFacade.findAll());
        return "eventList";
    }

}
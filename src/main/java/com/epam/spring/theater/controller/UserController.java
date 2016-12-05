package com.epam.spring.theater.controller;

import com.epam.spring.theater.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    private UserFacade userFacade;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getAllUsers(ModelMap model) {
        model.addAttribute("users", userFacade.findAll());
        return "userList";
    }

}
package com.epam.spring.theater.controller;

import com.epam.spring.theater.dto.EventDto;
import com.epam.spring.theater.dto.UserDto;
import com.epam.spring.theater.facade.EventFacade;
import com.epam.spring.theater.facade.UserFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private EventFacade eventFacade;

    @Autowired
    private UserFacade userFacade;

    @RequestMapping(value = "/events", method = RequestMethod.POST)
    public String multiFileEventUpload(@RequestParam("fileUpload") CommonsMultipartFile[] fileUpload, ModelMap model) throws IOException {
        if (validateUploadRequest(fileUpload)) {
            model.addAttribute("error", "Missing file");
            return "upload";
        } else {
            List<EventDto> events = extractEventDtos(fileUpload, EventDto.class);
            eventFacade.saveList(events);
            model.addAttribute("events", eventFacade.findAll());
            return "eventList";
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String multiFileUserUpload(@RequestParam("fileUpload") CommonsMultipartFile[] fileUpload, ModelMap model) throws IOException {
        if (validateUploadRequest(fileUpload)) {
            model.addAttribute("error", "Missing file");
            return "upload";
        } else {
            List<UserDto> users = extractEventDtos(fileUpload, UserDto.class);
            userFacade.saveList(users);
            model.addAttribute("users", userFacade.findAll());
            return "userList";
        }
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String multiFileEventUpload(ModelMap model) throws IOException {
        model.addAttribute("action", "events");
        return "upload";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String multiFileUserUpload(ModelMap model) throws IOException {
        model.addAttribute("action", "users");
        return "upload";
    }

    private boolean validateUploadRequest(CommonsMultipartFile[] fileUpload) {
        for (CommonsMultipartFile file : fileUpload) {
            if (file.getSize() == 0) {
                return true;
            }
        }
        return false;
    }

    private <T> List<T> extractEventDtos(CommonsMultipartFile[] fileUpload, Class<T> dtoClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, dtoClass);
        List<T> events = new ArrayList<>();
        for (CommonsMultipartFile aFile : fileUpload) {
            events.addAll(mapper.readValue(aFile.getBytes(), collectionType));
        }
        return events;
    }

}
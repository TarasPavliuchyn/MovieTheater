package com.epam.spring.theater.controller;

import com.epam.spring.theater.dto.EventDto;
import com.epam.spring.theater.facade.EventFacade;
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

    @RequestMapping(value = "/events", method = RequestMethod.POST)
    public String multiFileUpload(@RequestParam("fileUpload") CommonsMultipartFile[] fileUpload, ModelMap model) throws IOException {
        if (validateUploadRequest(fileUpload)) {
            model.addAttribute("error", "Missing file");
            return "upload";
        } else {
            List<EventDto> events = extractEventDtos(fileUpload);
            eventFacade.saveList(events);
            model.addAttribute("events", eventFacade.findAll());
            return "eventList";
        }
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String multiFileUpload(ModelMap model) throws IOException {
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

    private List<EventDto> extractEventDtos(CommonsMultipartFile[] fileUpload) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, EventDto.class);
        List<EventDto> events = new ArrayList<>();
        for (CommonsMultipartFile aFile : fileUpload) {
            events.addAll(mapper.readValue(aFile.getBytes(), collectionType));
        }
        return events;
    }

}
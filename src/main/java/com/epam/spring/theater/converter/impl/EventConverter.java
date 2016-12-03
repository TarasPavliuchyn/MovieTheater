package com.epam.spring.theater.converter.impl;

import com.epam.spring.theater.converter.AbstractConverter;
import com.epam.spring.theater.dao.EventDao;
import com.epam.spring.theater.dto.EventDto;
import com.epam.spring.theater.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventConverter extends AbstractConverter<Event, EventDto> {

    @Autowired
    private EventDao eventDao;

    @Override
    public EventDto convertToDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setName(event.getName());
        eventDto.setRating(event.getRating());
        eventDto.setBasePrice(event.getBasePrice());
        return eventDto;
    }

    @Override
    public Event convertToModel(EventDto eventDto) {
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setRating(eventDto.getRating());
        event.setBasePrice(eventDto.getBasePrice());
        return event;
    }
}

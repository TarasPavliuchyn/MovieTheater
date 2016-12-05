package com.epam.spring.theater.facade.impl;

import com.epam.spring.theater.converter.impl.EventConverter;
import com.epam.spring.theater.dto.EventDto;
import com.epam.spring.theater.facade.EventFacade;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventFacadeImpl implements EventFacade {

    @Autowired
    private EventConverter eventConverter;

    @Autowired
    private EventService eventService;

    @Override
    public void saveList(List<EventDto> eventDtos) {
        List<Event> events = eventConverter.toModelList(eventDtos);
        events.forEach(eventService::create);
    }

    @Override
    public Collection<EventDto> findAll() {
        List<Event> events = eventService.findAll().stream().collect(Collectors.toList());
        Collections.sort(events, Comparator.comparing(Event::getName));
        return eventConverter.toDtoList(events);
    }
}

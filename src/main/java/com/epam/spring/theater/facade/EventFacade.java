package com.epam.spring.theater.facade;

import com.epam.spring.theater.dto.EventDto;

import java.util.Collection;
import java.util.List;

public interface EventFacade {

    void saveList(List<EventDto> eventDtos);

    Collection<EventDto> findAll();
}

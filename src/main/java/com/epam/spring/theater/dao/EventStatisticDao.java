package com.epam.spring.theater.dao;

import com.epam.spring.theater.model.EventStatistic;

public interface EventStatisticDao extends CrudDao<EventStatistic, Integer> {
    EventStatistic findByEventName(String eventName);
}

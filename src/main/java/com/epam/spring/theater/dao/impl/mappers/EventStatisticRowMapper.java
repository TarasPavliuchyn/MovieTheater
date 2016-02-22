package com.epam.spring.theater.dao.impl.mappers;

import com.epam.spring.theater.model.EventStatistic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventStatisticRowMapper implements RowMapper<EventStatistic> {
    @Override
    public EventStatistic mapRow(ResultSet resultSet, int i) throws SQLException {
        EventStatistic eventStatistic = new EventStatistic();
        eventStatistic.setEventStatisticId(resultSet.getInt("event_statistic_id"));
        eventStatistic.setEventName(resultSet.getString("event_name"));
        eventStatistic.setBookedCount(resultSet.getInt("booked_count"));
        eventStatistic.setPriceQueryCount(resultSet.getInt("price_query_count"));
        eventStatistic.setAccessedCount(resultSet.getInt("access_count"));
        return eventStatistic;
    }
}

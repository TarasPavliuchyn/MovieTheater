package com.epam.spring.theater.dao.impl;

import com.epam.spring.theater.dao.EventStatisticDao;
import com.epam.spring.theater.model.EventStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EventStatisticDaoImpl implements EventStatisticDao {

    private static final String INSERT_QUERY = "INSERT INTO event_statistic (event_name, price_query_count, access_count, booked_count) VALUES (?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE event_statistic SET price_query_count=?, access_count=?, booked_count=? WHERE event_name=?";
    private static final String SELECT_BY_NAME = "SELECT * from event_statistic WHERE event_name=?";
    private static final String SELECT_ALL_QUERY = "SELECT * from event_statistic";
    private static final String DELETE_QUERY = "DELETE from event_statistic WHERE event_id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public EventStatistic create(EventStatistic model) {
        Object[] params = {model.getEventName(), model.getPriceQueryCount(), model.getAccessedCount(), model.getBookedCount()};
        jdbcTemplate.update(INSERT_QUERY, params);
        return model;
    }

    @Override
    public EventStatistic findByEventName(String eventName) {
        EventStatistic eventStatistic;
        try {
            eventStatistic = jdbcTemplate.queryForObject(SELECT_BY_NAME, new Object[]{eventName}, new EventStatisticRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return eventStatistic;
    }

    @Override
    public EventStatistic update(EventStatistic model) {
        Object[] params = {model.getPriceQueryCount(), model.getAccessedCount(), model.getBookedCount(), model.getEventName()};
        jdbcTemplate.update(UPDATE_QUERY, params);
        return model;
    }

    @Override
    public List<EventStatistic> findAll() {
        List<EventStatistic> eventStatistics = jdbcTemplate.query(SELECT_ALL_QUERY, new EventStatisticRowMapper());
        return eventStatistics;
    }

    @Override
    public void remove(EventStatistic model) {
        jdbcTemplate.update(DELETE_QUERY, model.getEventName());
    }

    @Override
    public EventStatistic find(Integer eventId) {
        throw new UnsupportedOperationException("not implemented");
    }

    static class EventStatisticRowMapper implements RowMapper<EventStatistic> {
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

}
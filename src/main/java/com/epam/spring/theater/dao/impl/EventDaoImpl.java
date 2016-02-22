package com.epam.spring.theater.dao.impl;

import com.epam.spring.theater.dao.EventDao;
import com.epam.spring.theater.model.Auditorium;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class EventDaoImpl implements EventDao {

    private static final String INSERT_QUERY = "INSERT INTO event (event_name, base_price, rating) VALUES (?,?,?)";
    private static final String INSERT_SCHEDULE_QUERY = "INSERT INTO schedule (event_name, auditorium, event_date) VALUES (?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE event SET event_name=?, base_price=?, rating=? WHERE event_id=?";
    private static final String SELECT_BY_NAME = "SELECT * FROM event LEFT JOIN schedule ON event.event_name = schedule.event_name WHERE event.event_name=? ";
    private static final String SELECT_BY_ID = "SELECT * FROM event WHERE event_id=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM event LEFT JOIN schedule ON event.event_name = schedule.event_name";
    private static final String DELETE_QUERY = "DELETE FROM event WHERE event_name=?";
    private static final String SELECT_DATE_RANGE = "SELECT * FROM event LEFT JOIN schedule ON event.event_name = schedule.event_name " +
            "WHERE schedule.event_date BETWEEN ? AND ?";
    private static final String SELECT_TO_DATE = "SELECT * FROM event LEFT JOIN schedule ON event.event_name = schedule.event_name " +
            "WHERE schedule.event_date <= ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Event create(Event event) {
        Object[] params = {event.getName(), event.getBasePrice(), event.getRating().name()};
        jdbcTemplate.update(INSERT_QUERY, params);
        insertSchedule(event);
        return event;
    }

    @Override
    public Event update(Event event) {
        Object[] params = {event.getName(), event.getBasePrice(), event.getRating().name(), event.getEventId()};
        jdbcTemplate.update(UPDATE_QUERY, params);
        insertSchedule(event);
        return event;
    }

    @Override
    public Event find(String eventName) {
        Map<Integer, Event> eventMap = jdbcTemplate.query(SELECT_BY_ID, new Object[]{eventName}, new EventResultSetExtractor());
        return eventMap.values().stream().findFirst().orElse(null);
    }

    @Override
    public void remove(Event event) {
        jdbcTemplate.update(DELETE_QUERY, event.getName());
    }

    @Override
    public Event getByName(String name) {
        Event event;
        try {
            Map<Integer, Event> eventMap = jdbcTemplate.query(SELECT_BY_NAME, new Object[]{name}, new EventResultSetExtractor());
            event = eventMap.values().stream().findFirst().orElse(null);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return event;
    }

    @Override
    public Collection<Event> findAll() {
        Map<Integer, Event> eventMap = jdbcTemplate.query(SELECT_ALL_QUERY, new EventResultSetExtractor());
        return eventMap.values();
    }

    @Override
    public Collection<Event> getForDateRange(Date fromDate, Date toDate) {
        Object[] params = {new java.sql.Date(fromDate.getTime()), new java.sql.Date(toDate.getTime())};
        Map<Integer, Event> eventMap = jdbcTemplate.query(SELECT_DATE_RANGE, params, new EventResultSetExtractor());
        return eventMap.values();
    }

    @Override
    public Collection<Event> getNextEvents(Date toDate) {
        Object[] params = {new java.sql.Date(toDate.getTime())};
        Map<Integer, Event> eventMap = jdbcTemplate.query(SELECT_TO_DATE, params, new EventResultSetExtractor());
        return eventMap.values();
    }

    @Override
    public void assignAuditorium(Event event, Auditorium auditorium, Date date) {
        event.getSchedule().put(date, auditorium.getName());
        Object[] params = {event.getName(), auditorium.getName(), date};
        jdbcTemplate.update(INSERT_SCHEDULE_QUERY, params);
    }

    static class EventResultSetExtractor implements ResultSetExtractor<Map<Integer, Event>> {

        @Override
        public Map<Integer, Event> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Integer, Event> eventMap = new HashMap<>();
            while (resultSet.next()) {
                int eventId = resultSet.getInt("EVENT_ID");
                Event event = eventMap.get(eventId);
                if (event == null) {
                    event = new Event();
                    event.setEventId(eventId);
                    event.setName(resultSet.getString("EVENT_NAME"));
                    event.setRating(Rating.valueOf(resultSet.getString("RATING")));
                    event.setBasePrice(resultSet.getBigDecimal("BASE_PRICE"));
                    eventMap.put(eventId, event);
                }
                String auditoriumName = resultSet.getString("auditorium");
                Date date = resultSet.getDate("event_date");
                event.getSchedule().put(date, auditoriumName);
            }
            return eventMap;
        }

    }

    private void insertSchedule(Event event) {
        Map<Date, String> schedule = event.getSchedule();
        for (Map.Entry<Date, String> entry : schedule.entrySet()) {
            jdbcTemplate.update(INSERT_SCHEDULE_QUERY, new Object[]{event.getName(), entry.getValue(), entry.getKey()});
        }
    }

}

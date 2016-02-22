package com.epam.spring.theater.dao.impl;

import com.epam.spring.theater.dao.TicketDao;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TicketDaoImpl implements TicketDao {

    private static final String INSERT_QUERY = "INSERT INTO ticket (booked, discounted, purchased, ticket_price, event_date, event_id) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE ticket SET booked=?, discounted=?, purchased=?, ticket_price=?, event_date=?, event_id=? WHERE ticket_id=?";
    private static final String SELECT_BY_NAME = "SELECT * FROM event LEFT JOIN schedule ON event.event_id = schedule.event_id WHERE event.event_name=? ";
    private static final String SELECT_BY_ID = "SELECT * FROM event WHERE event_id=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM ticket LEFT JOIN event ON event.event_id = ticket.event_id";
    private static final String SELECT_ALL_PURCHASED_QUERY = "SELECT * FROM ticket LEFT JOIN event ON event.event_id = ticket.event_id WHERE ticket.event_id=? AND ticket.event_date= ?";
    private static final String DELETE_QUERY = "DELETE FROM event WHERE event_name=?";
    private static final String SELECT_DATE_RANGE = "SELECT * FROM event LEFT JOIN schedule ON event.event_id = schedule.event_id " +
            "WHERE schedule.event_date BETWEEN ? AND ?";
    private static final String SELECT_TO_DATE = "SELECT * FROM event LEFT JOIN schedule ON event.event_id = schedule.event_id " +
            "WHERE schedule.event_date <= ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Ticket> getPurchasedTickets(Event event, Date date) {
        Object[] params = {event.getEventId(), date};
        List<Ticket> tickets = jdbcTemplate.query(SELECT_ALL_PURCHASED_QUERY, params, new TicketRowMapper());
        return tickets;
    }

    @Override
    public Ticket create(Ticket ticket) {
        Object[] params = {ticket.isBooked(), ticket.isDiscounted(), ticket.isPurchased(), ticket.getTicketPrice(), ticket.getDateTime(), ticket.getEventId()};
        jdbcTemplate.update(INSERT_QUERY, params);
        return ticket;
    }

    @Override
    public Ticket update(Ticket ticket) {
        Object[] params = { ticket.isBooked(), ticket.isDiscounted(), ticket.isPurchased(), ticket.getTicketPrice(), new java.sql.Date(ticket.getDateTime().getTime()), ticket.getEventId(), ticket.getEventId()};
        jdbcTemplate.update(UPDATE_QUERY, params);
        return ticket;
    }

    @Override
    public Ticket find(Integer id) {
        return null;
    }

    @Override
    public Collection<Ticket> findAll() {
        List<Ticket> tickets = jdbcTemplate.query(SELECT_ALL_QUERY, new TicketRowMapper());
        return tickets;
    }

    @Override
    public void remove(Ticket model) {

    }

    static class TicketRowMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setTicketId(resultSet.getInt("ticket_id"));
            ticket.setBooked(resultSet.getBoolean("booked"));
            ticket.setDiscounted(resultSet.getBoolean("discounted"));
            ticket.setPurchased(resultSet.getBoolean("purchased"));
            ticket.setTicketPrice(resultSet.getBigDecimal("ticket_price"));
            ticket.setDateTime(resultSet.getDate("event_date"));
            ticket.setEventId(resultSet.getInt("event_id"));
            return ticket;
        }
    }
}

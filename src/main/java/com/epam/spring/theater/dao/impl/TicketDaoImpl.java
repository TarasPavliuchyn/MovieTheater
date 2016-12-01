package com.epam.spring.theater.dao.impl;

import com.epam.spring.theater.dao.TicketDao;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Ticket;
import com.epam.spring.theater.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public class TicketDaoImpl implements TicketDao {

    private static final String INSERT_QUERY = "INSERT INTO ticket (booked, discounted, purchased, ticket_price, event_date, event_id, user_id, seat) VALUES (?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE ticket SET booked=?, discounted=?, purchased=?, ticket_price=?, event_date=?, event_id=?, user_id=?, seat=? WHERE ticket_id=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM ticket LEFT JOIN event ON event.event_id = ticket.event_id";
    private static final String SELECT_ALL_PURCHASED_QUERY = "SELECT * FROM ticket LEFT JOIN event ON event.event_id = ticket.event_id WHERE event.event_name=? AND ticket.event_date= ?";
    private static final String SELECT_BOOKED_TICKETS = "SELECT * FROM ticket LEFT JOIN event ON event.event_id = ticket.event_id WHERE ticket.user_id=?";
    private static final String FIND_TICKET_BY_ID = "SELECT * FROM ticket  WHERE ticket_id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Ticket> getPurchasedTickets(String eventName, Date date) {
        Object[] params = {eventName, date};
        List<Ticket> tickets = jdbcTemplate.query(SELECT_ALL_PURCHASED_QUERY, params, new TicketRowMapper());
        return tickets;
    }

    @Override
    public Ticket create(Ticket ticket) {
        Object[] params = {ticket.isBooked(), ticket.isDiscounted(), ticket.isPurchased(), ticket.getTicketPrice(), ticket.getDateTime(), ticket.getEventId(), ticket.getUserId(), ticket.getSeat()};
        jdbcTemplate.update(INSERT_QUERY, params);
        return ticket;
    }

    @Override
    public Ticket update(Ticket ticket) {
        Object[] params = {ticket.isBooked(), ticket.isDiscounted(), ticket.isPurchased(), ticket.getTicketPrice(), new java.sql.Date(ticket.getDateTime().getTime()), ticket.getEventId(),  ticket.getUserId(), ticket.getSeat(), ticket.getTicketId()};
        jdbcTemplate.update(UPDATE_QUERY, params);
        return ticket;
    }

    @Override
    public Ticket find(Integer ticketId) {
        Ticket ticket;
        try {
            ticket = jdbcTemplate.queryForObject(FIND_TICKET_BY_ID, new Object[]{ticketId}, new TicketRowMapper());
        } catch (EmptyResultDataAccessException e) {
            ticket = null;
        }
        return ticket;
    }

    @Override
    public Collection<Ticket> findAll() {
        List<Ticket> tickets = jdbcTemplate.query(SELECT_ALL_QUERY, new TicketRowMapper());
        return tickets;
    }

    @Override
    public List<Ticket> getBookedTickets(Integer userId) {
        return jdbcTemplate.query(SELECT_BOOKED_TICKETS, new Object[]{userId}, new TicketRowMapper());
    }

    @Override
    public void remove(Ticket model) {
        throw new UnsupportedOperationException("not supported");
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
            ticket.setUserId(resultSet.getInt("user_id"));
            ticket.setSeat(resultSet.getInt("seat"));
            return ticket;
        }
    }
}

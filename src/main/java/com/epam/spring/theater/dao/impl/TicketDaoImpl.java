package com.epam.spring.theater.dao.impl;

import com.epam.spring.theater.dao.TicketDao;
import com.epam.spring.theater.model.Event;
import com.epam.spring.theater.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String INSERT_QUERY = "INSERT INTO ticket (booked, discounted, purchased, ticket_price, event_date, event_id, user_id) VALUES (?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE ticket SET booked=?, discounted=?, purchased=?, ticket_price=?, event_date=?, event_id=?, user_id=? WHERE ticket_id=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM ticket LEFT JOIN event ON event.event_id = ticket.event_id";
    private static final String SELECT_ALL_PURCHASED_QUERY = "SELECT * FROM ticket LEFT JOIN event ON event.event_id = ticket.event_id WHERE ticket.event_id=? AND ticket.event_date= ?";
    private static final String SELECT_BOOKED_TICKETS = "SELECT * FROM ticket LEFT JOIN event ON event.event_id = ticket.event_id WHERE ticket.user_id=?";

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
        Object[] params = {ticket.isBooked(), ticket.isDiscounted(), ticket.isPurchased(), ticket.getTicketPrice(), ticket.getDateTime(), ticket.getEventId(), ticket.getUserId()};
        jdbcTemplate.update(INSERT_QUERY, params);
        return ticket;
    }

    @Override
    public Ticket update(Ticket ticket) {
        Object[] params = {ticket.isBooked(), ticket.isDiscounted(), ticket.isPurchased(), ticket.getTicketPrice(), new java.sql.Date(ticket.getDateTime().getTime()), ticket.getEventId(), ticket.getEventId(), ticket.getUserId()};
        jdbcTemplate.update(UPDATE_QUERY, params);
        return ticket;
    }

    @Override
    public Ticket find(Integer id) {
        throw new UnsupportedOperationException("not supported");
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
            return ticket;
        }
    }
}

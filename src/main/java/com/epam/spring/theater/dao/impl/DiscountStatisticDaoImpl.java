package com.epam.spring.theater.dao.impl;

import com.epam.spring.theater.dao.DiscountStatisticDao;
import com.epam.spring.theater.model.DiscountStatistic;
import com.epam.spring.theater.model.DiscountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Repository
public class DiscountStatisticDaoImpl implements DiscountStatisticDao {

    private static final String INSERT_QUERY = "INSERT INTO discount_statistic (discount_type, applied_count, user_id) VALUES (?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE discount_statistic SET discount_type=?, applied_count=?, user_id=? WHERE discount_statistic_id=?";
    private static final String SELECT_BY_ID = "SELECT * from discount_statistic WHERE discount_statistic_id=?";
    private static final String SELECT_BY_USER_AND_TYPE = "SELECT * from discount_statistic WHERE user_id=? AND discount_type=?";
    private static final String SELECT_ALL_QUERY = "SELECT * from discount_statistic";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public DiscountStatistic create(DiscountStatistic model) {
        Object[] params = {model.getDiscountType().name(), model.getAppliedCount(), model.getUserId()};
        jdbcTemplate.update(INSERT_QUERY, params);
        return model;
    }

    @Override
    public DiscountStatistic update(DiscountStatistic model) {
        Object[] params = {model.getDiscountType().name(), model.getAppliedCount(), model.getUserId(), model.getDiscountStatisticId()};
        jdbcTemplate.update(UPDATE_QUERY, params);
        return model;
    }

    @Override
    public DiscountStatistic find(Integer id) {
        DiscountStatistic discountStatistic;
        try {
            discountStatistic = jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{id}, new DiscountStatisticRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return discountStatistic;
    }

    @Override
    public Collection<DiscountStatistic> findAll() {
        List<DiscountStatistic> discountStatistics = jdbcTemplate.query(SELECT_ALL_QUERY, new DiscountStatisticRowMapper());
        return discountStatistics;
    }

    @Override
    public void remove(DiscountStatistic model) {
        throw new UnsupportedOperationException("not supported");
    }

    @Override
    public DiscountStatistic findByUserIdAndType(Integer userId, DiscountType discountType) {
        DiscountStatistic discountStatistic;
        try {
            discountStatistic = jdbcTemplate.queryForObject(SELECT_BY_USER_AND_TYPE, new Object[]{userId, discountType.name()}, new DiscountStatisticRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return discountStatistic;
    }


    static class DiscountStatisticRowMapper implements RowMapper<DiscountStatistic> {
        @Override
        public DiscountStatistic mapRow(ResultSet resultSet, int i) throws SQLException {
            DiscountStatistic discountStatistic = new DiscountStatistic();
            discountStatistic.setDiscountStatisticId(resultSet.getInt("discount_statistic_id"));
            discountStatistic.setDiscountType(DiscountType.valueOf(resultSet.getString("discount_type")));
            discountStatistic.setAppliedCount(resultSet.getInt("applied_count"));
            discountStatistic.setUserId(resultSet.getInt("user_id"));
            return discountStatistic;
        }
    }
}

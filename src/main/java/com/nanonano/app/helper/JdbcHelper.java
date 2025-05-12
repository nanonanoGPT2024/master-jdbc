package com.nanonano.app.helper;

import java.util.Arrays;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.nanonano.app.exception.ErrorHandler;
import com.nanonano.app.model.PagingResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JdbcHelper {

    private final JdbcTemplate jdbcTemplate;

    public JdbcHelper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <T> PagingResult<T> queryListPaging(
            String sql,
            Object params,
            RowMapper<T> rowMapper,
            int page,
            int limit,
            String orderBy,
            String order) {

        try {
            int offset = (page - 1) * limit;

            if (order == null || order.isEmpty()) {
                order = "ASC";
            }

            if (!order.equals("asc") && !order.equals("desc")) {
                order = "ASC";
            }

            String paginatedSql = sql + " ORDER BY " + orderBy + " " + order + " LIMIT ? OFFSET ?";
            if (params == null) {
                params = new Object[] {};
            }

            Object[] paramsArray = (Object[]) params;
            Object[] newParams = Arrays.copyOf(paramsArray, paramsArray.length + 2);
            newParams[paramsArray.length] = limit;
            newParams[paramsArray.length + 1] = offset;

            List<T> data = jdbcTemplate.query(paginatedSql, newParams, rowMapper);

            String totalData = "select count(*) from (" + sql + ") a";

            Long totalItems = jdbcTemplate.queryForObject(totalData, (Object[]) params, Long.class);

            int totalPages = (int) Math.ceil((double) totalItems / limit);

            return new PagingResult<>(data, totalPages, totalItems);
        } catch (DataAccessException e) {
           
            throw new ErrorHandler(500, "Internal server error");
        }
    }

    public <T> BeanPropertyRowMapper<T> beanMapper(Class<T> clazz) {
        return new BeanPropertyRowMapper<>(clazz);
    }

}
package com.nanonano.app.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GenericRepositoryImpl implements GenericRepository {

    private final JdbcTemplate jdbcTemplate;

    // public GenericRepositoryImpl(JdbcTemplate jdbcTemplate) {
    // this.jdbcTemplate = jdbcTemplate;
    // }

    @Override
    public <T> List<T> executeQuery(String sql, Object[] params, RowMapper<T> rowMapper) {
        long start = System.currentTimeMillis();
        List<T> result = jdbcTemplate.query(sql, params, rowMapper);
        long end = System.currentTimeMillis();
        log.info("Query executed in {} ms", (end - start));
        return result;

    }

    @Override
    public <T> Stream<T> streamQuery(String sql, Object[] params, RowMapper<T> rowMapper) {
        log.info("Streaming SQL Query: {}", sql);
        log.debug("With Parameters: {}", (Object) params);
        return jdbcTemplate.queryForStream(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps;
        }, rowMapper);
    }

    @Override
    public List<Map<String, Object>> executeQueryForMap(String sql, Object[] params) {
        log.info("Executing SQL Query For Map: {}", sql);
        log.debug("With Parameters: {}", (Object) params);
        return jdbcTemplate.queryForList(sql, params);
    }

    public <T> Optional<T> executeQueryForOptional(String sql, Object[] params, RowMapper<T> rowMapper) {
        try {
            List<T> results = jdbcTemplate.query(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
                return ps;
            }, rowMapper);
            return results.isEmpty() ? Optional.empty() : Optional.ofNullable(results.get(0));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    // }

    @Override
    public Map<String, Object> executeQueryForObjectMap(String sql, Object[] params) {
        log.info("Executing SQL QueryForObject Map: {}", sql);
        log.debug("With Parameters: {}", (Object) params);
        return jdbcTemplate.queryForMap(sql, params);
    }

}

package com.nanonano.app.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.jdbc.core.RowMapper;

public interface GenericRepository {
    <T> List<T> executeQuery(String sql, Object[] params, RowMapper<T> rowMapper);

    <T> Stream<T> streamQuery(String sql, Object[] params, RowMapper<T> rowMapper);

    List<Map<String, Object>> executeQueryForMap(String sql, Object[] params);

    <T> Optional<T> executeQueryForOptional(String sql, Object[] params, RowMapper<T> rowMapper);

    Map<String, Object> executeQueryForObjectMap(String sql, Object[] params);

}

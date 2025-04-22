package com.nanonano.app.service;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.nanonano.app.dto.UserDto;
import com.nanonano.app.repository.GenericRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final GenericRepository genericRepository;

    public List<UserDto> getUsers(int page, int size, String search, String sortBy, String sortDir) {
        int offset = page * size;

        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM users1 WHERE 1=2");

        if (search != null && !search.isBlank()) {
            sql.append(" AND name LIKE ?");
            params.add("%" + search + "%");
        }

        // Order by (pastikan field aman dari SQL Injection)
        List<String> allowedSortFields = List.of("id", "name", "email", "created_at");
        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "id";
        }

        String direction = sortDir.equalsIgnoreCase("desc") ? "DESC" : "ASC";

        sql.append(" ORDER BY ").append(sortBy).append(" ").append(direction);
        sql.append(" LIMIT ? OFFSET ?");

        params.add(size);
        params.add(offset);

        return genericRepository.executeQuery(
                sql.toString(),
                params.toArray(),
                new BeanPropertyRowMapper<>(UserDto.class));
    }

    public List<Map<String, Object>> getUsersRaw(int page, int size, String search, String sortBy, String sortDir) {
        int offset = page * size;

        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM users1 WHERE 1=1");

        if (search != null && !search.isBlank()) {
            sql.append(" AND name LIKE ?");
            params.add("%" + search + "%");
        }

        List<String> allowedSortFields = List.of("id", "name", "email");
        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "id";
        }

        String direction = sortDir.equalsIgnoreCase("desc") ? "DESC" : "ASC";

        sql.append(" ORDER BY ").append(sortBy).append(" ").append(direction);
        sql.append(" LIMIT ? OFFSET ?");

        params.add(size);
        params.add(offset);

        return genericRepository.executeQueryForMap(
                sql.toString(),
                params.toArray());
    }

    public Optional<UserDto> getUserById(int id) {
        String sql = "SELECT * FROM users1 WHERE id = ?";
        try {
            Optional<UserDto> user = genericRepository.executeQueryForOptional(
                    sql,
                    new Object[] { id },
                    new BeanPropertyRowMapper<>(UserDto.class));

            System.out.println(user.isPresent());
            if (user.isPresent()) {
                System.out.println("User found: " + user.get());
                user.ifPresent(u -> System.out.println("Nama : " + u.getName()));
                // System.out.println("User name: " + a);
            } else {
                System.out.println("User not found with ID: " + id);
                return Optional.empty();
            }
            return user;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("User not found: " + e.getMessage());
            return Optional.empty();
        }

        
    }

    public Map<String, Object> getUserByIdRaw(int id) {
        String sql = "SELECT * FROM users1 WHERE id = ?";
        return genericRepository.executeQueryForObjectMap(sql, new Object[] { id });
    }

}

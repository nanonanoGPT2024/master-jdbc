package com.nanonano.app.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nanonano.app.dto.UserDto;
import com.nanonano.app.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public List<UserDto> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return userService.getUsers(page, size, search, sortBy, sortDir);
    }

    @GetMapping("/raw")
    public List<Map<String, Object>> getUsersRaw(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return userService.getUsersRaw(page, size, search, sortBy, sortDir);
    }

    @GetMapping("/{id}/all")
    public Optional<UserDto> getUser(@PathVariable int id) {
        return userService.getUserById(id);

    }

    @GetMapping("/{id}/raw")
    public Map<String, Object> getUserRaw(@PathVariable int id) {
        return userService.getUserByIdRaw(id);
    }

}

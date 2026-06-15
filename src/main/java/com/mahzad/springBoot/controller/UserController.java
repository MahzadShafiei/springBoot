package com.mahzad.springBoot.controller;

import com.mahzad.springBoot.dto.CreateUserRequest;
import com.mahzad.springBoot.dto.UserResponse;
import com.mahzad.springBoot.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public List<UserResponse> create(@RequestBody CreateUserRequest user) {
        return  userService.createUser(user);
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll();
    }
}


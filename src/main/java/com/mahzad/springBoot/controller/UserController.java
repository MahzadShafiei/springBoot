package com.mahzad.springBoot.controller;

import com.mahzad.springBoot.dto.CreateUserRequest;
import com.mahzad.springBoot.dto.UserResponse;
import com.mahzad.springBoot.model.User;
import com.mahzad.springBoot.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/getAll")
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("/getAllByJpql")
    public List<UserResponse> getAllByJpql() {
        return userService.getAllByJpql();
    }

    @GetMapping("/getUserByIdJpql")
    public Optional<UserResponse> getUsers(int id) {
        return  userService.getUserByIdJpql(id);
    }

    @GetMapping("/getUsersByRoleJpql")
    public List<UserResponse> getUsersByRole(int roleId) {
        return  userService.getUsersByRoleJpql(roleId);
    }
}


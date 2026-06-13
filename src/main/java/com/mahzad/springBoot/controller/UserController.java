package com.mahzad.springBoot.controller;

import com.mahzad.springBoot.dto.CreateUserRequest;
import com.mahzad.springBoot.model.User;
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

    //@PostMapping
    //public User create(@RequestBody User user) {
    //    return userService.save(user);
    //}

    @PostMapping
    public User create(@RequestBody CreateUserRequest user) {
        return  userService.createUser(user);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }
}


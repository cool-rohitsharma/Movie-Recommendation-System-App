package com.movierecommendationapp.movierecommendation.controller;

import com.movierecommendationapp.movierecommendation.domain.model.Users;
import com.movierecommendationapp.movierecommendation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Users getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public Users saveUser(@RequestBody Users user) {
        return userService.saveUser(user);
    }
}

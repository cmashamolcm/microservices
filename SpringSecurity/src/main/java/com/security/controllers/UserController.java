package com.security.controllers;

import com.security.models.User;
import com.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin(value = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}")
    public User getUser(@PathVariable(name = "id") String userId){
        return userService.getUser(userId);
    }
}

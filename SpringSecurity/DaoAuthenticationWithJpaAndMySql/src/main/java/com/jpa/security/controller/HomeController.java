package com.jpa.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home(){
        return "<h1>Home World</h1>";
    }

    @GetMapping("/user")
    public String user(){
        return "<h1>User World</h1>";
    }

    @GetMapping("/admin")
    public String admin(){
        return "<h1>Admin World</h1>";
    }
}

package com.sso.facebook;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
//@RequestMapping("/app")
public class HomeController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello facebook user";
    }

    @GetMapping("/hai")
    public String hai(){
        return "Hai facebook user";
    }

    @GetMapping("/login")
    @PermitAll
    public String login(){
        return "Login facebook user";
    }
}

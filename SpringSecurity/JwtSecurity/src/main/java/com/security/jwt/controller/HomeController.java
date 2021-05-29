package com.security.jwt.controller;

import com.security.jwt.models.AuthenticationRequest;
import com.security.jwt.models.AuthenticationResponse;
import com.security.jwt.service.AuthenticationService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class HomeController {

    @Autowired
    AuthenticationService authenticationService;

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

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) throws AuthenticationException {
       return authenticationService.authenticate(request);
    }


}

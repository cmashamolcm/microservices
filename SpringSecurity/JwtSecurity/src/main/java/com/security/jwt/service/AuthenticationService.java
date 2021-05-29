package com.security.jwt.service;

import com.security.jwt.models.AuthenticationRequest;
import com.security.jwt.models.AuthenticationResponse;
import com.security.jwt.util.JwtUtil;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    public ResponseEntity<?> authenticate(AuthenticationRequest request) throws AuthenticationException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        }catch (Exception ex){
            throw new AuthenticationException("Invalid user - 401");
        }

        return generateTokenForValidUser(request);
    }

    private ResponseEntity<?> generateTokenForValidUser(AuthenticationRequest request) {
        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(user);
        System.out.println("Token = " + token);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}

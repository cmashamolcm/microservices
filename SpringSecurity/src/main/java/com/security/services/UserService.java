package com.security.services;

import com.security.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User getUser(String userId) {
        return new User("Asha", "Mol", "Bangalore");
    }
}

package com.security.services;

import com.security.models.Credentials;
import com.security.models.UserPrinciple;
import com.security.repo.CredentialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CredentialService implements UserDetailsService {
    @Autowired
    CredentialRepo credentialRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credentials credentials = credentialRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        return new UserPrinciple(credentials);
    }
}

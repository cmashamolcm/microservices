package com.security.repo;

import com.security.models.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepo extends JpaRepository<Credentials, String> {
    Optional<Credentials> findByUsername(String username);
}

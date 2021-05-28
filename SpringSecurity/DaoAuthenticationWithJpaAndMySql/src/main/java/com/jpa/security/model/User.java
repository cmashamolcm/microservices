package com.jpa.security.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "jpa_users")
public class User {
    @Id
    String username;

    String password;

    boolean enabled;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    List<Authority> authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}

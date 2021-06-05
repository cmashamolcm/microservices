package com.sso.facebook;

import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Profile("github")
//@EnableWebSecurity//
@Order(10)
//need to fix
public class SecurityConfigOnlyForGithub extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().antMatcher("/**").authorizeRequests()
                .antMatchers("/login").permitAll()
        .anyRequest().authenticated();
    }
}

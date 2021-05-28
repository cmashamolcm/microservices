package com.basic.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    //For authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //method chaining - builder pattern
        auth.inMemoryAuthentication()
                .withUser("ashamol")
                //.password("{noop}ashamol123") -if we didn't specify an encoder, if we just set {noop}, it will consider as noop encoder.
                // This is because while finding the encoding, it checks the id in {} attached to password if not specified explicitly.
                .password("ashamol123")
                .roles("USER")
        .and()// helps us to add more users.
        .withUser("mani")
        .password("mani123")
        .roles("ADMIN");//mani has admin role
    }

    @Bean
    public PasswordEncoder encoder(){
        return NoOpPasswordEncoder.getInstance();
                //PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //For authorization
    //.antMatchers("/**") is used to specify the path pattern
    // Wild card /**  indicates all levels from current level and next levels also. Ie; everything
    // /abc/** means all urls with /abc followed by anything
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //start from highest restriction to the min as order matters.
                .antMatchers("/admin", "/**").hasRole("ADMIN")//only admin can access/admin end point or can access anything
                .antMatchers("/user").hasRole("USER")//only USER can access user end point
                .antMatchers("/").permitAll()//this makes all use only localhost:8080
                .and()
                .formLogin();//Default is form login itself. Decides how to login.
                            //This will give a logout url also at localhost:8080/logout


    }
}

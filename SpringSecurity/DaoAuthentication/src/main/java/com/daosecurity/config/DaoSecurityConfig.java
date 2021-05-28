package com.daosecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class DaoSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource datasource;//will be automatically mapped to H2 since we added h2 lib.

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                //.dataSource(datasource);//--uncomment this and use if default schema and default table names like users, authorities are used.
                //If table name etc changes, use querying approach.
                .dataSource(datasource)
                .usersByUsernameQuery("select username, password, enabled from my_users where username= ?")
                .authoritiesByUsernameQuery("select username, authority from my_authorities where username= ?");
        //Uncomment and use if default schema to be enabled and users to be inserted via code.
                /*.dataSource(datasource)
                .withDefaultSchema()//security creates default schema for us.
                .withUser(User.withUsername("mani").password("1234567890").roles("ADMIN"))
                .withUser(User.withUsername("asha").password("1234567890").roles("USER"));*/
    }

    @Bean
    public PasswordEncoder encoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin", "/**").hasRole("ADMIN")
                .antMatchers("/").hasAnyRole()
                .and()
                .formLogin();
    }
}

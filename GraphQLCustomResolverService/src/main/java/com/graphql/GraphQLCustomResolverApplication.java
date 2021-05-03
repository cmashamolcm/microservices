package com.graphql;

import com.graphql.config.BookErrorHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GraphQLCustomResolverApplication {
    public static void main(String[] args) {
        SpringApplication.run(GraphQLCustomResolverApplication.class, args);
    }
}

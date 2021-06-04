package com.reactor.config;

import com.reactor.controller.FunctionalWebHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class FunctionalWebRouter {

    @Bean
    public RouterFunction<ServerResponse> route(FunctionalWebHandler handler){
        return RouterFunctions.route(RequestPredicates.GET("/functional/flux").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::getFlux)
                .andRoute(RequestPredicates.GET("/functional/mono").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::getMono)
                .andRoute(RequestPredicates.GET("/functional/normal").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::getNormal);


    }
}

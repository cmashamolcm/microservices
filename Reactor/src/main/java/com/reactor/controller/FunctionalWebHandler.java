package com.reactor.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class FunctionalWebHandler {

    public Mono<ServerResponse> getFlux(ServerRequest request){
        return ServerResponse.ok().body(Flux.just(1, 2, 3, 4), Integer.class);
    }

    public Mono<ServerResponse> getMono(ServerRequest request){
        return ServerResponse.ok().body(Mono.just(1), Integer.class);
    }


    public Mono<ServerResponse> getNormal(ServerRequest request){
        return ServerResponse.ok().body(10000, Integer.class);
    }

}

package com.reactor.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class FluxMonoController {

    @GetMapping("/flux")
    public Flux<String> getFlux(){
        return Flux.just("Asha","Mol", "C M")
                //.delayElements(Duration.ofMillis(5000))
                .log();
    }

    @GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<String> getFluxStream(){
        return Flux.just("Asha","Mol", "C M")
                .delayElements(Duration.ofMillis(5000))
                .log();
    }

    @GetMapping(value = "/fluxstreamnd", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<User> getFluxStreamWithNdJson(){
        return Flux.just(new User("Asha"),new User("Mol"), new User("C M"))
                .map(data->data)
                .delayElements(Duration.ofMillis(5000))
                .log();
    }

    @GetMapping(value = "/fluxstream-infinite", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> getFluxInfiniteStream(){
        return Flux.interval(Duration.ofSeconds(1))

                .log();
    }

    @GetMapping(value = "/mono", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Long> getMono(){
        return Mono.just(1l)
                .delayElement(Duration.ofSeconds(1))
                .log();
    }
}


class User{
    String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
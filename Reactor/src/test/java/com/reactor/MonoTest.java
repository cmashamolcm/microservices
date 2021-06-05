package com.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {

    @Test
    public void monoTest(){
        Mono.just("Ashamol C M")
                .map(data->data + " is my name...")
                .log()
                .subscribe((data)-> System.out.println(data), (error)-> System.out.println("error = " + error), ()-> System.out.println("Completed."));
    }

    @Test
    public void monoWithStepVerifyTest(){
        StepVerifier.create(Mono.just("Ashamol C M")
                .map(data->data + " is my name...")
                .log())
                .expectNext("Ashamol C M is my name...")
                .expectComplete()
                .verify();

    }

    @Test
    public void monoWithErrorTest(){
        StepVerifier.create(Mono.just("Ashamol C M")
                .map(data->data + " is my name...")
                .concatWith(Mono.error(new NullPointerException("Mono null")))
                .log())
                .expectNext("Ashamol C M is my name...")
                .expectError()
                .verify();

    }
}

package com.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

public class FluxTest {

    @Test
    public void firstFluxTest(){
        Flux<String> flux = Flux.just("Asha", "Mol", "C M");
        flux.map(data->data + " Flux Appended...")
                .subscribe((data)-> System.out.println(data));
    }

    @Test
    public void firstFluxWithOnErrorAndCompleteTest(){
        Flux<String> flux = Flux.just("Asha", "Mol", "C M");
        flux.map(data->data + " Flux Appended...")
                .subscribe((data)-> System.out.println(data), (data)-> System.out.println(data), ()-> System.out.println("Completed"));
    }

    @Test
    public void firstFluxWithErrorTest(){
        Flux<String> flux = Flux.just("Asha", "Mol", "C M");
        flux.concatWith(Flux.error(new RuntimeException("Contacted exception")))
                .map(data->data + " Flux Appended...")
                .subscribe((data)-> System.out.println(data), (error)-> System.out.println(error));
    }

    @Test
    public void firstFluxWithErrorAndLoggingEnabledTest(){
        Flux<String> flux = Flux.just("Asha", "Mol", "C M");
        flux.concatWith(Flux.error(new RuntimeException("Contacted exception")))
                .map(data->data + " Flux Appended...")
                .log()
                .subscribe((data)-> System.out.println(data), (error)-> System.out.println(error));
    }

    @Test
    public void firstFluxWithLoggingEnabledTest(){
        Flux<String> flux = Flux.just("Asha", "Mol", "C M");
                flux.map(data->data + " Flux Appended...")
                .log()
                .subscribe((data)-> System.out.println(data), (error)-> System.out.println(error));
    }

    @Test
    public void fluxReactorTest(){
        Flux<String> flux = Flux.just("Asha", "Mol", "C M")
                .log();
        StepVerifier.create(flux)
                .expectNext("Asha").as("first element")
                .expectNext("Mol").as("second element")
                .expectNext("C M").as("third element")
                .expectComplete()
                .verify();
    }

    @Test
    public void fluxWithErrorReactorTest(){
        Flux<String> flux = Flux.just("Asha", "Mol", "C M")
                .concatWith(Flux.error(new NullPointerException("Custom Null")))
                .log();
        StepVerifier.create(flux)
                .expectNext("Asha").as("first element")
                .expectNext("Mol").as("second element")
                .expectNext("C M").as("third element")
                //.expectError(NullPointerException.class)
                .expectErrorMessage("Custom Null")
                .verify();
    }

    @Test
    public void fluxWithNextCountAndErrorReactorTest(){
        Flux<String> flux = Flux.just("Asha", "Mol", "C M")
                .concatWith(Flux.error(new NullPointerException("Custom Null")))
                .log();
        StepVerifier.create(flux)
                .expectNextCount(3)
                .expectError()
                .verify();
    }

    @Test
    public void fluxExpectAllInOneLineReactorTest(){
        Flux<String> flux = Flux.just("Asha", "Mol", "C M")
                .concatWith(Flux.error(new NullPointerException("Custom Null")))
                .log();
        StepVerifier.create(flux)
                .expectNext("Asha", "Mol", "C M")
                .expectError()
                .verify();
    }

    @Test
    public void fluxExpectSequenceReactorTest(){
        List<String> dataSource = List.of("Asha", "Mol", "C M");
        System.out.println(dataSource.getClass());//----------ImmutableCollection.ListN
        Flux<String> flux = Flux.fromIterable(dataSource)
                .concatWith(Flux.error(new NullPointerException("Custom Null")))
                .log();
        StepVerifier.create(flux)
                .expectNextSequence(dataSource)
                .expectError()
                .verify();
    }
}

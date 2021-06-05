package com.reactor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

@WebFluxTest
@AutoConfigureWebTestClient(timeout = "36000")
public class FluxMonoControllerTest {

    @Autowired
    WebTestClient client;

    @Test
    public void testGetFlux(){
        Flux<String> flux = client.get().uri("/flux")
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody();
        StepVerifier.create(flux)
                .expectNext("AshaMolC M")
        .verifyComplete();
    }

    @Test
    public void testGetFluxStream(){
        Flux<String> flux = client
                .get().uri("/fluxstream")
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody();
        StepVerifier.create(flux)
                .expectNext("AshaMolC M")
                .verifyComplete();
    }


    @Test
    public void testGetFluxWithoutStepVerifier() {
        EntityExchangeResult<List<String>> flux = client
                .get().uri("/fluxstream")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(String.class)
                .returnResult();
        System.out.println(flux.getResponseBody());
    }

    @Test
    public void testInfiniteFluxStream(){
        StepVerifier.create(client.get().uri("/fluxstream-infinite")
                //.accept(MediaType.APPLICATION_STREAM_JSON) //----not mandatory
                .exchange()
                .returnResult(Long.class)
        .getResponseBody())
                .expectNext(0l, 1l, 2l)
                .thenCancel()
                .verify();

    }

    @Test
    public void testMono() {
        client.get().uri("/mono")
                .exchange()
                .expectBody(Long.class)
                .consumeWith(body-> System.out.println(body.getResponseBody()));
    }


}

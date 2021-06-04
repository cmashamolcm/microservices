package com.reactor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
public class FunctionalWebTest {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void fluxTest(){
        StepVerifier.create(webTestClient.get().uri("/functional/flux")
                .exchange()
                .returnResult(Integer.class).getResponseBody())
                .expectSubscription()
                .expectNext(1, 2, 3, 4)
                .verifyComplete();
    }
}

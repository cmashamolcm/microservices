package com.graphql.config;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;
import com.graphql.models.Author;
import com.graphql.models.Book;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;

@Component
public class BookSubscriptionResolver implements GraphQLSubscriptionResolver {
    @Autowired
    BookQueryResolver bookQueryResolver;

    public Publisher<Book> book() throws InterruptedException {
        return Flux.interval(Duration.ofSeconds(1)).map(p -> new Book(UUID.randomUUID().toString(), "Random Book", 100, new Author(UUID.randomUUID().toString(), "Random Author")));
    }
}

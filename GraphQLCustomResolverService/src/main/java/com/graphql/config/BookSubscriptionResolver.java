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

/**
 * url: http://localhost:8080/graphql
 * query:
 * subscription{
 *   book{
 *   	name
 *     id
 *     pageCount
 *     author{
 *       id
 *       firstName
 *     }
 *        }
 * }
 *
 * Response:
 * {
 *   "data": {
 *     "book": {
 *       "name": "Random Book",
 *       "id": "62a9f7ac-1ff4-43c8-8ae6-47f606db90be",
 *       "pageCount": 100,
 *       "author": {
 *         "id": "de42ac87-f37d-4757-9f7e-59bc255a7c74",
 *         "firstName": "Random Author"
 *       }
 *     }
 *   }
 * }
 */
@Component
public class BookSubscriptionResolver implements GraphQLSubscriptionResolver {
    @Autowired
    BookQueryResolver bookQueryResolver;

    public Publisher<Book> book() throws InterruptedException {
        return Flux.interval(Duration.ofSeconds(1)).map(p -> new Book(UUID.randomUUID().toString(), "Random Book", 100, new Author(UUID.randomUUID().toString(), "Random Author")));
    }
}

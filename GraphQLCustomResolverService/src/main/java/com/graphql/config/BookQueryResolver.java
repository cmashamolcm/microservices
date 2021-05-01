package com.graphql.config;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.graphql.models.Author;
import com.graphql.models.Book;
import org.springframework.stereotype.Component;

@Component
public class BookQueryResolver implements GraphQLQueryResolver {
    Book[] books = {new Book("book-1", "Harry Potter and the Philosopher's Stone", 225, new Author("author-1"))
            , new Book("book-2", "Harry Potter", 2250, new Author("author-2"))
    };

    //query name can be bookById. It maps to get + BookById while finding resolver.
    public Book getBookById(String id) {
        return books[0].getId().equals(id)?books[0]:books[1];
    }
}

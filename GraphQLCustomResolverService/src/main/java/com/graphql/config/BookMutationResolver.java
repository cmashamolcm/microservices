package com.graphql.config;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.graphql.models.Author;
import com.graphql.models.Book;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookMutationResolver implements GraphQLMutationResolver {
    public Book createBook(String name, String authorName, int pageCount){
        System.out.println(name + " : " + authorName + " : " + pageCount);
        return new Book("book-001", name, 500, new Author(UUID.randomUUID().toString(), authorName));
    }
}

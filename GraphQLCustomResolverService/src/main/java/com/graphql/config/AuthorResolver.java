package com.graphql.config;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.graphql.models.Author;
import com.graphql.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

//while calling getBookById or createBook, this resolver is not needed. Its for getAuthor through author(id: Int) query.
@Component
public class AuthorResolver implements GraphQLQueryResolver {
    @Autowired
    BookQueryResolver bookQueryResolver;

    public Author getAuthor(String name){
        return Arrays.stream(bookQueryResolver.books).filter(p->p.getAuthor().getFirstName().equals(name)).map(p->p.getAuthor()).findFirst().orElse(null);
    }
}

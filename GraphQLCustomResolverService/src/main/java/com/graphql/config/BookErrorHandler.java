package com.graphql.config;

import com.graphql.config.exception.BookError;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookErrorHandler implements GraphQLErrorHandler {
    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> list) {
        list.stream().forEach(error-> {
            System.out.println("Error exists");
            error.getExtensions().get("This is a book test error");
        });
        return list;
    }

    @Override
    public boolean errorsPresent(List<GraphQLError> errors) {
        //System.out.println("Inside is present");
        return !CollectionUtils.isEmpty(errors);
    }
}

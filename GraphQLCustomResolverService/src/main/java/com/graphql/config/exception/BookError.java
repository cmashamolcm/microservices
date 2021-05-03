package com.graphql.config.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import org.springframework.cache.Cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Make sure Throwable is extended. Else throw new BookError() won't work as GraphQLError is not sub of Throwable.
//Make sure to specify this as RuntimeException. If given as Throwable, handler will not work.
public class BookError extends RuntimeException implements GraphQLError {
    private final Map<String, Object> extensions = new HashMap<>();

    public BookError(String message) {
        super(message);
        extensions.put("error", "Message from book resolver");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }
}

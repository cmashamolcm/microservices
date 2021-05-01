package com.graphql.config;

import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import kotlin.text.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

//@Configuration
public class GraphqlProvider {
    private GraphQL graphQL;

    @Autowired
    private GraphqlDataFetcher graphqlDataFetcher;

    @Bean
    public GraphQL graphQL(){
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        String url = Resources.toString(Resources.getResource("schema.graphqls"), Charsets.UTF_8);
        this.graphQL =  GraphQL.newGraphQL(getSchema(url)).build();
    }

    @Bean
    public GraphQLSchema getSchema(String url){
        TypeDefinitionRegistry typeDefinition = new SchemaParser().parse(url);
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("bookById", graphqlDataFetcher.getBookById())).build();

        return new SchemaGenerator().makeExecutableSchema(typeDefinition, runtimeWiring);

    }
}

###GraphQL
****
1. Framework from Facebook -2012, open sourced after 2015
2. New standard for APIs - its better REST
3. Single end point to respond to multiple queries
4. It does declarative data fetching - client decides what data it needs exactly.

Why is GraphQL needed?
****
1. Need to data for mobile and app can vary. 
   Depending on the need, same API has to respond with different parts of same data 
   to manage faster and efficient data loading.
2. Variety of front end technologies
3. Rapid feature development needs
4. Can reduce network calls, data size etc.
5. No over or under fetching of data.
6. _Drawback_ is with rapid changes in UI data requirements, backend also have to undergo changes as only exact set of data is provided.

GraphQl vs REST:
****
REST is Representational State Transfer. It made stateless servers and structured resource access.
GraphQL is enhanced REST in one way.

Note:
Uses POST request to pass query to fetch data from server side.

###How it works:
****
Its fully based on schema. Schema helps to define contact between front end and backend.

###Parts of GraphQL:
****
1. SDL - Schema Definition Language. Defines types.
   
Note: Queries are used to fetch data while mutations are used to modify data.

####Queries:
****
    Eg: Simple types
    type Person{  
            name: String!
            age: Int!
            place: String   //no ! means not mandatory
        }
        type Post{
            content: String! // ! means mandatory field
        }


    Eg: Post have Person as author
        type Post{
            content: String!
            author: Person!
        }
        type Person{
            name: String!
            age: Int!
            place: String
            posts: [Post!]!
        }
    

###Sample Request And Responses:
****
Client sends expected schema within curly braces.

    {
    //allPersons is the request payload inside {}.
        allPersons{
            name
        }
    }
    //Response payload
    {
        "allPersons":[
            {"name" : "P1"},
            {"name" : P2"}
        ]
    }


Request and response payloads with multiple attributes.
      
         {
         //allPersons is the request payload inside {}.
         allPersons{
            name
            age
            }
         }

         //Response payload
         {
            "allPersons":[
                  {"name" : "P1", "age":10},
                  {"name" : P2", "age": 20}
            ]
         }
Request format to restrict the response with criteria.
         
         {
            allPersons(last: 2){// last 2 items from database
               name
            }
         }

Request format to get another type also within response body.

         {
            allPersons(last: 2){// last 2 items from database
               name
               posts {
                  content
               }
            }
         }
         response:
         {
            allPersons:[
               {  "name": "P1", 
                  "posts":[
                     {"content": "hai from P1"},
                     {"content": "bye from P1"},
                  ]
               },
               {  "name": "P2", 
                  "posts":[
                     {"content": "hai from P2"},
                     {"content": "bye from P2"},
                  ]
               },
               {  "name": "P3", 
                  "posts":[]
               }
            ]
         }

####Mutations:
****
 3 types:
 1. Insert/ Create new data
 2. Update/ Modify existing
 3. Delete existing


1. Create: 
   
         Request:
            mutation {
               createPerson(name: "P4", age: 40){
                  name
                  age
               }
            }
         Response:
            {
               "createPerson":{
                  "name": "P4",
                  "age": 40
               }
            }
2.Note: If we specify ID! as the type of an attribute in schema, server auto generates it.

      Eg: schema: 
            type Person{
               name: String!
               id: ID!
               age: Int
            }

            Mutation request:
            mutation{
                  createPerson(name: "P5"){
                     id
                  }
            }
         This indicates that return id back once object is created.
         Response:
            {
               "createPerson":{
                  "id": 1234567890
               }
            }
3. Real time _subscriptions_:
   
   Use:
   
         subscription {
            newPerson{
               name
            }
         }
         Server responds pushing data when each user getting created.
         Response:
         {
            "newPerson": {
                        "name":"P6"
                        }
         }

Schema is collection of graphQL types for an API.
Schema will have <B>root types</B>.
Root types defines API entry points.
Eg: 
   type Query {

   }
   type Mutation {

   }
   type Subscription {

   }

1. Query Type:
   
         If Request wants to be like:
         {
            allPersons{
               name
            }
         }
         or
         {
            allPersons(last: 2){
               name
            }
         }      

         Query should be defined as:
         type Query{
            allPersons(last: Int): [Person!]!
         }
         If we have another query to get all Posts,
         define schema as
         type Query{
            allPersons(last: Int): [Person!]!
            allPosts(last: Int): [Post!]!
         }
2. Mutation Type:

         mutation{
            createPerson("name": "P7", "age": 10){
               id
            }
         }
         For this, define root query as:
         type Mutation{
            createPerson(name: String!, age: String): Person!
         }
         If we had update and delete also,
         type Mutation{
            createPerson(name: String!, age: String): Person!
            updatePerson(id: ID!, name: String!, age: String!): Person!
            deletePerson(id: ID!): Person!
         }

3. Subscription Type:
         
         Expected Request:
   
         subscription{
            newPerson{
               name
               age
            }
         }
         Required subscription root type:
         type Subscription{
            newPerson : Person!
         }
         If more subscriptions required,
         type Subscription{
            newPerson : Person!
            deletedPerson: Person!
         }

Combining everything in 1, 2, 3 and definition of Person as well as Post is becoming the **schema**.

**Root types** are Query, Mutation, Subscription which are defining the APIs.

###Resolver Functions:
Those functions capable of to return the value corresponding to the fields mentioned in query.
Eg:   

      Request:
         {
            User(id: 1234567890){
               name
               age
               posts(first: 5): {
                  content
               }
            }
         }
      This need resolvers for
      1. Get user by id. (User(id): User!)
      2. Get name. (name(User): String!)
      3. Get age. (age(User): Int!)
      4. Get first 5 posts of this user. posts(first: Int, user: User!): [Post]!

Note:
****
GraphQL client side / front end can cache the data after normalizing to improve performance.
Default Resolvers are there for fields with same name in schema usually. Server provides it.

Steps Performed by GraphQL Server to resolve a query and respond:
1. Parse the query
2. Validate the query based on schema
3. Execute the query with the help of resolvers in a tree like structure from top to bottom.
   
      
               user(id)
                  |
      name           age         posts
      |*                          |
                  content, user/author, date
                     |*          |        |*
                              name
                                 |*
      |* means its resolved.
   
   All resolvers will be responding with promises and aggregates at the end to get final result.
   Nodes of the tree in same level can resolve in parallel.
   
Note:
   
Only when GraphQLSchema is enabled as bean, then only /graphql endpoint gets enabled.

Note:

If we are not defining a resolver for a subtype and trying to fetch the data through a type which refers to it, error comes while querying that subtype resolver is not there.
Eg: BookQueryResolver needs an AuthorResolver if we are trying to fetch Book(id, name, author). But no error comes if we need Book(id, name).


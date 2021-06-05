## Reactor:

1. Why this came?
    * SpringMVC is the main module that helps to create web applications.
    * As the number of services interact increases, the response time get affected by it.
    * Thread per request model is followed in Spring
        * server.tomcat.max-threads is to change number of threads to handle requests.
        * Default max-threads = 200.
        * Can change by config.
        * If we increase the thread count, it will lead to more memory usage as it need stacks(commonly 1MB per thread)
        * This creates bottle-neck
        * One solution to this is horizontal scaling with load balancing etc.
    * Replace "thread per request model"
    * Another issue is due to different speeds and storage limitations, 
      may be some applications have to be given feed back to be in same pace as of 
      other app.
      This is done by <b>back-pressure</b>.
      It enables to give feedback to a database to avoid overflow of data to the app 
      while getting all entries from a table with millions of row.
    
2. Imperative vs Reactive Styles:
   * Imperative is blocking and synchronous.
     May be we can use <b>callback</b> which will execute a method once the lengthy action is done.
     But this callback methods will not return anything. 
     Mostly API endpoints expect something as response and hence we cannot go with it everytime.
     There is a high chance for callback hell which is due to multiple callbacks back to back.
     
     Another option is Future which can return Future object.
     From Java8, there is CompletableFuture as well.
     So, since REST follows imperative style of programming, it suffers below issues.
     * Imperative is synchronous and blocking
     * Limit for concurrent users
     * No back pressure support
     
   * Reactive is async and non-blocking, supports back pressure, replaced thread per request concept, based on processing of stream of data
      * Based <b>Even driven stream</b>
      * Two types of outcomes, onComplete() or error().
3. Reactive Programming:
   * Reactive programming is a programming paradigm which is based on event driven streams and to support async, non-blocking execution.
   * Back pressure is also a key feature of it. This gives feedback to the data source to control data flow speed.
   * 3 types of data flows:
      1. onNext(item) -----data stream event
      2. onComplete() -----success event 
      3. onError() ------error event
4. Reactive Streams Specification/ Rules/ Concepts:
   By Netflix, Pivotal etc (reactor core)
   * <b>Publisher</b>
         This indicates the producer of data. It is representation of data source
         Eg: DataBase

         public interface Publisher<T>{
            public void subscribe(Subscriber<s super T> s);
         }
   
   * <b>Subscriber</b>
         The one who subscribes and use the data as consumer.
     
         public interface Subscriber<T>{
            public void onSubscribe(Subscription s);
            public void onNext(T t);
            public void onComplete();
            public void onError(Thowable t);
         }
   * <b>Subscription</b> 
         The interface representing the connection between data source and subscriber.
         
         public interface Subscription{
            public void request(long n);   -----------to specify number of items to flow in case of back pressure
            public void cancel();
         }
   * <b>Processor</b> 
         Interface that extends both Subscriber and Publisher.
     
         public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {
         }
     
   * Event Flow Between Subscriber & Publisher:
         
         |--------------------------------------------------------------------------|
         |                                                                          |
         |                                   publisher                              |
         |--------------------------------------------------------------------------|
            ^           |              ^              |                |         |
            |           |              |              |                |         |
            |           |              |              |                |         |-------------|
         subscribe() 1  |              subscription   |        n events|                       |
            |           |              .request(n) 3  |                |                       |
            |           |              |              onNext(data) 4   onNext(data)            |onComplete() 5
            |           |              |              |                |                       |
            |           |              |              |                |                       |
            |           |              |              |                |                       |
            |           |              |              |----->|---------V-----------------------V----------------|        
            |           |              |-------------------->|                                                  |
            |           |Subscription 2                      |                                                  |
            |           |----------------------------------->|                                                  |
            |                                                |                                                  |
            |                                                |                subscriber                        |
            |----------------------------------------------->|                                                  |
                                                             |--------------------------------------------------|
   
5. Reactive Libraries:
      * Build on top of reactive specification
      * Consists of Publisher, Subscriber, Processor, Subscription implementations.
      * Eg:
         1. rxJava     ----- From Netflix
         2. reactor    ----- From Pivotal, Project Reactor used with Springboot
         3. Java9- Flow class
      * Reactor has multiple module
        Eg:
         1. Reactor Core
         2. Reactor Test
         3. Reactor Netty      (netty - framework to help easy development of network servers, clients etc with various protocols like TCP, UDP etc. Eg: Netty is used in Cassandra and other distributed systems)
                               (tomcat - application server. It is based on Java servlets and JSP concepts. It can receive requests and respond with data. Not only as HTML, but with JSON, XML etc also)
                               (jetty - web server and servlet container. It will respond with HTML for HTTP request. Subset of App Server. From Eclipse.)
         4. Reactor Kafka etc...
    
Note: When we add reactor libs into the spring-boot app, netty is used instead of tomcat by default.
   
6. Reactor Core:
   * Core lib with reactive stream specification
   * Flux and Mono are two <b>reactor types</b> of implementations. Ie; Publisher implementations.
   * Flux - 0...n elements
   * Mon - 0...1 elements
    
7. Springboot Project Setup:
    1. Add springboot-starter-webflux, reactor-test, starter-test, lombok (this need enabling of annotation processor in IDE and add in maven-compiler-plugin)
    2. Can start with Flux and Mono then. 
    
    There are <b>reactor stack</b> and <b>servlet stack</b>
    There are reactive repositories for MongoDB, Cassandra etc with SpringBoot 2.0 and spring 5.
    WebFlux is there similar to MVC, reactive security similar to spring security etc in reactive stack.
    
8. In reactor, subscribe(consumer for onNext, consumer for onError, Runnable for onComplete)
    Smart way of reusing Runnable.
    rxJava uses Action interface with a method run()for onComplete.
9. Testing with Reactor Test:
        
        Flux<String> flux = Flux.just("Asha", "Mol", "C M")
                .log();
        StepVerifier.create(flux)
                .expectNext("Asha").as("first element")
                .expectNext("Mol").as("second element")
                .expectNext("C M").as("third element")
                .expectComplete()
                .verify();
        We have to give verify() at the end to trigger the stream data flow.
        Else, no logs or no verification happens.
        If we change order, it will be giving error.
        
        StepVerifier.create(flux); gives StepVerifier.FirstStep on which we cannot apply simply verify(). But onError or onComplete only we can apply verify() as it is returning StepVerifier itself.

    We can use
        .expectNext("data")
        .expectComplete()
        .verify();
   
        .expectNext("data")
        .expectError(ErrorClass)
        .verify();

        .expectNext("data")
        .expectErrorMessage("error message")
        .verify();

10. When we return a Flux or Mono from our controllers, browsers acts like subscriber.       
    But if there is a delay in response stream, the browser will be blocked till is comes back.
    To avoid this situation, we have to specify media type as STREAM JSON.
    
        Eg:
        @GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
        public Flux<String> getFluxStream(){
            return Flux.just("Asha","Mol", "C M")
            .delayElements(Duration.ofMillis(5000))
            .log();
        }
11. Now MediaType.APPLICATION_STREAM_JSON_VALUE is deprecated from Spring 5.3 and use <b>MediaType.APPLICATION_NDJSON</b>
    ndjson means New Line Delimited JSON.
    This can be managed in front end with promises etc. If we try in browser, it just downloads file of our stream.
12. When we use .delayElements(Duration.ofMillis(5000)), tests will fail if timeout is not increased for WebTestClient.
    Add @AutoConfigureWebTestClient(timeout = "36000") on top of the test class to get rid of this problem.
    
13. How to set up endpoint to fetch infinite stream:

        @GetMapping(value = "/fluxstream-infinite", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
        public Flux<Long> getFluxInfiniteStream(){
            return Flux.interval(Duration.ofSeconds(1))
            .log();
        }

    This helps us to expose endpoints that can listen infinitely. Eg: stock price ticker, notification etc.
    We should ensure that the delay of emission should be higher like 1sec so that each time browser is able to receive the values on time.
    In such case, error comes "Could not emit tick due to lack of requests"
    
    To test it;
            
        @Test
        public void testInfiniteFluxStream(){
            StepVerifier.create(client.get().uri("/fluxstream-infinite")
                        .exchange()
                        .returnResult(Long.class)
                        .getResponseBody())
                        .expectNext(0l, 1l, 2l)
                        .thenCancel() //-------------cancel to close the stream while testing
                        .verify();
        }
14. Functional Web:
    * New functional way of handling requests and responses
    * Two main components.
        1. Handler Function 
           Receives ServerRequest
           Returns ServerResponse
           This is equivalent to body of our normal functions to process request and return response.
        2. Router Function
            Similar to @RequestMapping, @GetMapping.
           Like a single method to process our request
           
    * Router Function redirects to the exact url and Handler Function performs the request processing and returns response.
    * Eg:
    

    Sample Handler Function:
    ------------------------
    @Component
    public class FunctionalWebHandler {

        public Mono<ServerResponse> getFlux(ServerRequest request){
            return ServerResponse.ok().body(Flux.just(1, 2, 3, 4), Integer.class);
        }

        public Mono<ServerResponse> getMono(ServerRequest request){
            return ServerResponse.ok().body(Mono.just(1), Integer.class);
        }

    }

    Sample Router Function:
    -----------------------
    @Configuration
    public class FunctionalWebRouter {

        @Bean
        public RouterFunction<ServerResponse> route(FunctionalWebHandler handler){
            return RouterFunctions.route(RequestPredicates.GET("/functional/flux").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::getFlux)
                .andRoute(RequestPredicates.GET("/functional/mono").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::getMono);

        }
    }

   * Even though there is no hard and fast compile time rule to set Mono/ Flux or any other Publisher as body of ServerResponse in handler function,
     if we try with any other type like Integer etc, it will give error at runtime
     java.lang.IllegalArgumentException: 'producer' type is unknown to ReactiveAdapterRegistry
     
           
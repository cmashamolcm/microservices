# microservices
####Front Controller
######Front controller is a design pattern used in spring MVC. This controller will be the single handlder for all requests coming to the application.
It will be having 3 components.
1. Front Controller - Single point of contact for centralized request handling
2. Dispatcher - Helps front controller to route the requests to respective handlers.
3. View - Return objects from front controller. It holds html, css, data in older spring apps.

In RESTFul Web services, dispatcher servlets will return data model instead of view.


Front Controller(DispatcherServlet) -----------> Dispatches request to controllers -------respond with model
                                ^                                |
                                |   | <---domain object-----------|
           return control       |   |
                                    v
                                Render response view


####Controller vs RestController:
1. Controller is there from beginning. RestController from 4.x
2. Controller returns view vs Rest returns domain object as Html or JSON. Previously it used to return logical view name and view resolver will return the actual view(JSP, Freemarker etc) with data to send in HTTP response.
3. Rest Controller = Controller +  Request body.

Note:
Jackson 1 or 2 is used for parsing models to http messages or json objects to hold is response.


###Micronaut vs Springboot
1. Springboot is more popular
2. Micronaut is more cloud native. Eg: in built support for service discovery, load balancing, discributed tracing etc.
   There is in-built messaging system support like Kafka in micronaut
3. Micronaut has less memory usage, footprint, loading time
4. Management(actuator etc) is better in springboot
5. Config support is more in springboot

##SOLID Principle: - How to design classes
<B>1. S</B>ingle Responsibility

<B>2. O</B>pen for extension closed for modification


<B>3. L</B>iskov Substitution - If B is child of A, anywhere A is used should be replaceable with B

<B>4. I</B>nterface Segregation - Many client specific interfaces are better than single general-purpose one

<B>5. D</B>ependency Inversion - Classes should inherit from abstract classes or interfaces instead of concrete classes

###Dependency Injection vs Inversion Of Control:
1. DI is a way to implement IoC.
2. IoC is more on object life cycle controlled by framework. Eg: A framework decides which implementation to be picked based on the configurations
   vs DI is injecting the object of a class via setters(when dependency is optional) or constructors(mandatory dependencies) or field based(@Autowired)
3. AOP and DI are types of IoC
4. In IoC, context will have all required objects to pick instead of create new. It helps plug n play with different frameworks

IoC means framework or running program controls the object lifecycle.
                           
                            IoC
                             |
            ------------------------------------------
    Service Locator     Events      Delegates       DI
                                                    |----Constructor Injection
                                                    |----Method Injection
                                                    |----Propery/ Field Injection
                                                         modes  |-----no autowiring
                                                                |-----byName: based on name of abean
                                                                |-----byType: based on class name. Only one such bean expected. Else throws exception
                                                                |-----byConstructor: based on type of param in constructor
Autowiring:
Easy but it used reflection. So,costly.Also, it is threat to single responsibility as any number of autowire can be done in a class
Wiring auto detects from existing beans.
4 modes of autowiring is there.


###Abstract classes vs Interfaces after Java8:
1. Abstract classes are still useful. It will have a constructor and state.
2. Default method cannot use methods of other classes as interface cannot hold a non-static, non-final, non-public object in it.

Note:
Always try to use default methods instead of abstract classes.
to use default method in one interface in multiple inheritance problem, use
A.super.defautMethod(); inside child C.


###HTTP 1.1 vs 2:
Http1 sends data in plain text format vs http2 in binary format

Http2 has multiplex, compression, prioritization, caching etc.

Note: In 1.0, one tcp connection per request-response was there. It got chaged in 1.1


###Java 8, 9, 10, 11:
Java8: Lambda, Functional interface, streams, method reference etc
Java9: Modularity to help microservices, try catch with resources defined anywhere before try catch
       JShell, private static methods in interface, immutable collections by .of() gives unmodifiable collection, modularity with requires and export annotations
Java10: var, Collectors.unmodifiableList()
Java11: String .isEmpty(), lines(), Files.readString(), writeString(), Optional.of().isEmpty()



###Why used OpenJDk14?
1. Enhancement in G1 GC, ZGC  and garbage collection
2. Currency updates
3. package * to direct import
            

   

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

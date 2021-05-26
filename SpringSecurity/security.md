##Spring Security:
****
1. Framework to plug and play various type of security mechanisms into our web app.
2. Authentication vs Authorization:
    * Authentication is validating user credentials. Checks if user has permissions to login.
    * Authorization is allowing read/write permissions, based on role.
3. When we add spring-boot-starter-security, security will be enabled with basic auth.
    From postman, we can set basic auth with username "user" and password obtained from app console.
   Ie; with just adding of jar, it gives basic authentication and security.
4. How can we configure in memory user name and password
    Use WebSecurityConfigurerAdaptor
5. We can use DaoAuthenticationProvider if credentials are to be fetched from database.  
6. OAuth2 Integration:
    * Better notto use our own database for credentials to avoid hacking issues
    * To support login with google, facebook etc.
    * OAuth is an authorization framework to enable access based on some third party credentials.
    * How it works?
        * Google/ Facebook will provide a token once login is success in their end.
        * Our app rely on that token for user validation.
   








Notes:

1. @SpringbootApplication:
      Configuration,
      EnableAutoConfiguration,
      ComponentScan
   
2. @RestController:
      Controller
      ResponseBody
   
3. @Autowired:
      Used to inject dependency beans
   
4. Stereotype Annotations:
      @Service, @Controller, @Repository(helps to convert Unchecked exceptions in DA layer to convert into DataAccessException):
         From Component.
      Adds beans to context.
      @Service("s) means the bean created will be referred as s.
   
5. HttpMessageNotWritableException:
    * It can come if we have to configured proper parsing library.
    * It can come if there is no public getters and setters or default constructor for DTO returned.
    * Default media type is JSON, so its trying to access JSON parsing libs and end up not getting valid lib or valid getters/ setters for models.
6. @EnableWebSecurity:
    * Gives on top of @Configuration class.
    * Indicates to disable default security and use our own custom implementation of WebSecurityConfigurerAdapter.
    * It adds the annotated class to security context.
7. When we configure UserDetailsService with InMemoryUserDetailsManager, default noops encoder works. Either we have to pass {noop} in password we configure or
   have to explicitly create an encoder from PasswordEncoderFactories to get rid of the issues of "no key found for null" kind of errors.
8. @Id is mandatory in a model class. Else, it will give error.

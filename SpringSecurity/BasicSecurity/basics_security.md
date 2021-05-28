###Application Security:
****
1. How to secure the app end points
2. 2 questions
        * Who are you?
        * What do you want?
3. What is Spring Security does?
    * Login and logout user
    * Allow/ Deny logged-in user to access urls
    * Allow url access based on roles
    * It helps to get rid of known set of vulnerabilities like cross site request forgery(csrf - forces valid users to do some invalid things), session fixation(stealing of session id and using it) etc.
    * It can give SSO, credential based authentication, authorization etc.
    * Method level security
4. Core Concepts of Web Security:
    * Authentication:
      
        * Validate the user for identity.
        * <b>"Who is this user?"</b> is answered here with proof like password.
        * There can be users with accounts/ ids in our application. They are verified here.
        * Types of authentication:
            1. knowledge based(through verification of password, pin-number, secret questions etc). It's simple but  not safe.
            2. Possession based(through OTP in our phone, cards)
            3. Multi-factor : Mix of knowledge + possession
        
    * Authorization:
      
        * <b>Is the user allowed to do this?</b> is answered.
        * The user may want something which he is not allowed to. Eg: entering into server room
        * Based on permissions, it will be allowed or denied.
        * Decides on accessibility of resources.
        * User have to be authenticated first to check if he has permission to perform something.
    
    * Principal:
      
        * The currently logged on user
   
    * Granted Authority:
        * Set of access permissions attached to a user.
        * Eg: IT dept. persons can have granted permission to enter server room
        * So, entering server room is a granted authority for IT dept person
        * "What they can do?"
     
    * Roles:
        * A group of authorities
        * If a person is assigned with a role, he will be automatically assigned with certain set of authorities.
5. How is Spring security works?
      * It is like a watch man.
      * It uses filters to achieve the task.
      * Filters are cross-cutting concerns
6. What happens when we add springboot-starter-security jar?        
      * Spring security adds mandatory authentication by default
      * Adds login form
      * Handles login error
      * Creates user and sets a password printed in console.
7. To set custom default user name and password,
      * Add spring.security.user.name
      * Add spring.security.user.password in yml file.
      * application.yaml file can be configured as below to make custom default credentials.      
   
               spring:
                  security:
                        user:
                           name: asha
                           password: asha123
   
8. Adding Authentication: (401-Authentication Error)
      * The key player here is <b>AuthenticationManager</b>
      * We have to configure Authentication manager instance to customize or override the default behavior.
      * <b>AuthenticationManagerBuilder</b> helps us to create AuthenticationManager.
      * Create a <b>SecurityConfig extending WebSecurityConfigurerAdapter</b> which has configure() method accepting AuthenticationManagerBuilder.
      * Put <b>@EnableWebSecurity</b> ver the class. This annotation has @Configuration also as part of it.
      * It's mandatory to set a password encoder, role as well.
        If role is not set, error comes in starting time itself(Granted Authority cannot be null).
        If encoder is not set, error comes while hitting after entering the credentials.
      * Else error comes as <i>"There is no PasswordEncoder mapped for the id "null"</i>
      * We just have to create a bean of password encoder within Config class. No need to add it. it will be automatically taken.
            
            
         @Bean
         public PasswordEncoder encoder(){
            return NoOpPasswordEncoder.getInstance();
            //PasswordEncoderFactories.createDelegatingPasswordEncoder();
         }

9. Adding Authorization: (403 - Authorization Error -Forbidden)   
      * Grand authority
      * This is done through HttpSecurity
      * So we can use configure(httpSecurity) method in WebSecurityConfigurerAdaptor.
      * Override this method.
      * Set patterns and roles to access them.
   
10. Internal Working:
      * Uses filters
      * They are crosscutting concerns
      * There can be n servlets each for each url
      * Filters come before the call reaches servlets
      * There are SpringSecurityFilter even before reaching other filter
      * Default filter to which /** goes is DelegatingFilterProxy
      * DelegatingFilterProxy is routing the calls to multiple other security filters like authentication filter, authorization filters etc.
      * AuthenticationFilter accepts credentials and validates creates Authentication object and based on response from AuthenticationManager it sets Principal for valid credentials in same Authentication object and kept it in thread local's security context.
         
            Application Server
            --------------------------------------------------------
            |Web App
            |  |-----------------------------------------------
            |  |  Filters          | servlet 1     | servlet 2
            |  |       | |               
            |  |       | |           |               |
            |  |       | |
            |  |       | |          | servlet n-1   | servlet n
            |  |       1 2
            |  |
            --------------------------------------------------------
    

            Request---->DelegatingFilterProxy------>Security Filters(authentication fiter + authorization filters)
                                                                                                            |       
                                                                                                            V
                                                                                        If security filter is authentication filter;
                                                                                                Authentication Filter (accepts "Authentication" object holding the credentials)
                                                                                                            |-------------------------------------------------->Calls AuthenticationManager (a subtype of ProviderManager) that calls  --------------------> all configured AuthenticationProviders' authenticate() method for validation if respective AuthenticationProvider's support() method returns true.
                                                                                                            |<------------------------------------------------------If validation succeeds<--------------------------------------------                                                                                                    This support() method returns true, if passed credential object type is supported by it.                                             
                                                                                                            V
                                                                                                Set userdetails object as principal in Authentication Object on success                                                                                            These authentication providers are plugguable.
                                                                                                            |                                                                                                                                                                    What they does is;              |---------------------------------------
                                                                                        <-------------------|                                                                                                                                                                                                    | Authentication Provider--------username----------->generic service which interacts with <------------------>any type of identity store
                                                               This object will be kept in security context in thread local of the request.|                                                                                                                                                                     |                        <-----------User Object-----     This is done by (UserDetailsService having loadUserByUserName() method which can do what ever it want to connect to identity store)
                                                                                                                                                                                                                                                                                                                 |
                                                                                                                                                                                                                                                                                                                 |(Note: any pluggable auth provider have to ave it's own authentication provider as well as UserDetailsService to fetch data from identity store of it, may from database, a map in memory etc.
                                                                                                                                                                                                                                                                                                                 | Mostly same user details object will be returned as principal as principal is never a class, it can be any object with current user. 
                                                                                                                                                                                                                                                                                                                 | UserDetails object will be having username, password, garnted authorities list, isEnabled() means is active, method to check password expiry etc.         
                                        For avoiding login for each url, this context can be shifted to session. This will be done by another filter.
    
11. JDBC Based Authentication:
    * Can configure a data source
    * SpringSecurity allow to create and configure default schema and users as below.
      Just running the app after adding h2 etc will easily give us the datasource andsecurity.
      
                
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.jdbcAuthentication()
                .dataSource(datasource)
                .withDefaultSchema()//security creates default schema for us.
                .withUser(User.withUsername("mani").password("1234567890").roles("ADMIN"))
                .withUser(User.withUsername("asha").password("1234567890").roles("USER"));
        }

  * But we can configure our own external schema as well.
  * There is default schema defined in https://docs.spring.io/spring-security/site/docs/3.0.x/reference/appendix-schema.html
  * which can be used to store user and role.
  * This the structure of tables getting created when ".withDefaultSchema()" is given.
  * To use custom schema,
        * define schema.sql, data.sql in resource folder as per schema indicated in https://docs.spring.io/spring-security/site/docs/3.0.x/reference/appendix-schema.html.
        * Start the app if h2 is configured
        * It will read from these files by default.
        * We will fill authorities tables make sure to specify roles as 'ROLE_NAMEOFROLE'.
        * Else login fails as whatever name given after ROLE_ only is taken while authorizing.
  * What if we want to configure a different table for user details.
        * Use .usersByUsernameQuery() method in AuthenticationManagerBuilder.
        * It's mandatory to have username, password, enabled to be fetched from custom user table.
        * Else error comes.
        * Eg: 
    
             @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.jdbcAuthentication()
                .dataSource(datasource)
                .usersByUsernameQuery("select username, password, enabled from my_users where username= ?")
                .authoritiesByUsernameQuery("select username, authority from my_authorities where username= ?");
   
12. Jpa Based Authentication:
    * Handy when we have our own databases and tables for authentication.
    * There is no ready-made AuthenticationProvider to plug in easily for external database authentication with jpa.
    * We have to use default AuthenticationProvider. But we can customize the UserDetailsService to contact the identity provider ie; database.
    * <b>If we want to have a custom authentication implementation, may be we can use default AuthenticationProvider and plug our own custom UserDetailsService.</b>
    
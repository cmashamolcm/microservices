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

13. LDAP Authentication:
    * LDAP is a protocol
    * Lightweight Directory Access Protocol
    * It provides easy way to store and access information such as organization hierarchy, employee details etc in tree structure.
    * It provides directory access through IP protocol
    * Add below dependencies.
        

        <!--This below dependency adds ldap-core as well. But 2.3.3 version is not ok with jdk16. So, use 2.3.4+ explicitly. Else error comes as java.naming is not accessible etc.-->
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-ldap</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.ldap</groupId>
            <artifactId>spring-ldap-core</artifactId>
            <version>2.3.4.RELEASE</version>
        </dependency>
        <!--This below dependency enables embedded ldap for dev test-->
        <dependency>
            <groupId>com.unboundid</groupId>
            <artifactId>unboundid-ldapsdk</artifactId>
        </dependency>

   * LDAP spring boot dependencies helps us to work with LDAP just like a datasource. 
   * It has its own repo, entities etc.
   * The file with .ldif is called <b>ldap data interchange format</b>
   * This holds the basic schema of a ldap configuration.
   * In simple use case, can use below authorization config.
     

     @Override
     protected void configure(HttpSecurity http) throws Exception {
     http
     .authorizeRequests()
     .anyRequest().fullyAuthenticated()
     .and()
     .formLogin();
     }
   * But in case, we want to have role based access, either have to use implementation of LdapAuthoritiesPopulator, which can fetch roles based on username from database etc.
   * It has a method getGrantedAuthorities(username).

14. JWT Based Authentication:
    * Json Web Token(pronounced as JAWT)
    * Extensively using in microservice development
    * Http is stateless
    * Authentication is per request. But it is annoying from a user perspective to enter credentials every time.
    * Tokens came into picture to solve this issue.
    * 2 types:
        * Session Tokens (maintains principal in session scope instead of request thread's  Thread local).
        * JSON Web Token
    * How is token based authentication working?


<b>With Session Tokens: Session ID + Cookies</b>

        Client browser                                                                          Web App
            |                                                                                     |                  
            |-----------------------------------------------------login-------------------------->|
            |                                                                                     |-----for successful authentication, generate a session which has all user info.  just like a ticket
            |<-----------------------------RETURN SESSIONID on success placing it in cookies<-----| 
            |     just like ticket id which can be told to web app again to verify the same user  |
            |                                                                                     |
            |------------next request's header containing cookie holding valid session ID-------->|------validate with session id by doing loopup in session log and proceed on success.


   * But the problem here is, this session log is in single web app. It is not shared. So, in microservice architecture where multiple services are there, this won't work unless we put it in a shared cache like Redis.
   * Else, one way is to use <b>"sticky sessions"</b> approach that makes load balancer intelligent enough so that it can route the calls with same session id to same service instance always.

<b>With JWT Tokens: JSON object holding user details signed by web app</b>
    * When we use session id approach, the problem is, web app side have to remember the session details.
    * This creates issues when multiple services' comes into picture because it is stored in some specific location.
    * It will be great of each request holds the session info fully.
    * But we cannot trust the end user that they will send us valid session info to the web app.
    * If after primary authentication, while sharing the session info, if authorittative signature is there, it will be trustworthy.
    

        Session ID + Cookies = reference token(session token holding reference to user info)
                client-----------------------login--------->validate user and collect details and create a tiket by front office person, strore it  
                      <----------------ticket id------------

                next time client visit----------ticket id---> retrieve ticket info by front office and validate
                                      <---------serve the request----

        JWT Token = Value Token (holding signed user info value):
                client-----------------------login--------->validate user and collect details and create a tiket by front office person, send ticket info + authroty signature instead of storing 
                      <-------signed ticket info as JSON-----------

                next time client visit----------signed ticket info---> validate sign and verify user
                                      <---------serve the request----

15. How is this JWT token looks like?
    
    * It likes like below sample separted by "<b>.</b>". The payload will be JSON encoded in base64.
    * Header is also base64 encoded. It holds type=JWT and algo to decode header.
    * Signature adds strength to it as anybody can encode or decode the payload and header.
    * But the signature is generated by the server/ web app and any change in payload or header will ensure that the signature will not match with the actual one while validating.  
    * The reason is, signature is generated by the encryption of encoded header and encoded payload as well as a private key/ secret holding at server side.
    * So, if an attacker changes payload and header, while validating the signature at server side, signature received and signature generated will mismatch and login fails.
    
    
    
    eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.       --------header
    eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.    ------payload
    SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c     --------signature


   * JWT Working:

        
            user-------------eneters credentials (login)------------------------->-----server validates
            |                                                                               |
            |                                                                               |  (valid user)  ------>gererate JAWT token        
            |                                       if fails 401 comes                      |                            |
    --------|<---------------send the JWT token-----<---------------------------------------|-------------<--------------|
      store it in local storage                                                             |
      or cookies                                                                            |
            |                                                                               |
            |--------------------send token in request header----------------->-------------|---------validate token------------------> Token validation Process
            |                   Authorization : Bearer<space>JAWTToken                      |               |                                   [JWT Token = base64 encoded hhhhhhhhhheader.base64 encoded pppppppppayload.encoded or non-encoded sssssssssignature
            |                                                                               |               |                                                   |       +        |                                                                      |
            |<--------------------------------proper response for valid requests------------|---------------|                                                   v                v                                                                      |
            |                                                                               |                                                                   apply private key / server side secret and encrypt to compute signature                 |
                                                                                                                                                                                                                                    |                   |
                                                                                                                                                                                                                                    |                   |
                                                                                                                                                                                                                                    v        <=>        v
                                                                                                                                                                                                                                If matches, success, else error 403         
    Note: * Do not put sensitive info in JSWt payload as it can be decoded.
          * If somebody gets the entire JWT token,they can reuse it if not changing anything in header or payload.
          * So, have to be careful to use JWT with HTTPS and other safe mechanisms like OAuth.
          * In session token, we could invalidate if a user logs out etc.
          * Since there is nothing kept in server side, we can keep expiry time for each token for safety.
          * For more safety, we can keep black listed tokens when a user logs out etc to avoid stealing of it.
          * When we enter user role in config(HttpSecurity) method, it should not have ROLE_ as prefix.
          * When we set UserDetails object, GantedAuthorities should be starting with ROLE_.
 
   * Encoding vs Encryption:
        * Encoding is convert from one format to another.
        * Mainly if data send by one system is not acceptable format for other system, conversion is to be done.
        * Encoding helps to reduce size, send data in a different format with the help of publicly available algorithms
        
        * Encryption is converting from one format to another with the help of a secret key.
        * It can be treated as an advanced encoding
        * This is to maintain secret between sender and receiver
        * There are symmetric and asymmetric encryption
        
16. JWT Implementing in Spring boot Project:
        * Add
    
            <dependency>
                <groupId>io.jsonwebtoken</groupId>
                <artifactId>jjwt</artifactId>
                <version>0.9.1</version>
            </dependency>
            <!--To get rid of java.lang.ClassNotFoundException: javax.xml.bind.DatatypeConverter while generating jwt token-->
            <dependency>
                <groupId>javax.xml.bind</groupId>
                <artifactId>jaxb-api</artifactId>
                <version>2.4.0-b180830.0359</version>
            </dependency>

    
   * Claims is a key-value pair which is holding any additional info to hold in a token.
    
   * Steps to set up authentication:
     1. 
        * Create /authenticate POST end point
        * Validate username and password with AuthenticationManager
        * If succeeds without exception
        * use JwtUtil to generate token and send it as response.
        * Note that we have to permitAll() for /authenticate
        * AuthenticationManager bean have to be enabled in SecurityConfig with overridden authenticationManagerBean() method annotated with @bean.
     2.
        * Once the response of /authenticate gives jwt token,
        * enable jwt token validation in each subsequent request
        * Get token in Authorization header by intercepting the request - Via filter extending OncePerRequestOnceFilter class
        * Validate token with Jwt secret
        * Process request in new Filter and validate and store jwt token in security context.
        * Add this filter before UserNamePasswordAuthenticationFilter by 
            
                http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //----- disabled session tokens and no state kept.
                http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);//-----added new filter
    
        * Now, filter is added for all requests. Login works fine as well.
        * Then call /authenticate POST method with valid credentials
        * Taken the token and set it in header of next requests and send it.

17. OAuth:
    * It is a standard for authorization used between services.
    * Ultimately,it is <b>access delegation</b> 
    * This can be treated like a valet parking of expensive cars where valet key will have limited access 
      whereas actual key will have full access.
      The owner can hand over the valet key to parking guy safely as it has limited access compared to original.
      Same way, a user can give a limited access key to an app to access some other account without full credential details shared to it.
    * Eg: One app tries to login with Google id. There, the user is not providing credentials to the app. 
      But it checks with google for confirmation without actual credentials exposed to the app.
    * OAuth 1.0 - old
    * OAuth 2.0 - the latest standard
    * OAuth is giving limited access to an unknown party to use some parts of our another valid account.
    * Eg: When we do an online payment, we get the bank transaction page. But that allows only this single transaction. Nothing more can be done.
    * Eg: login with Google account to some website.
    
    * OAuth Flow:
    
    ### OAuth == AuthO<i>rization</i> not Authentication

    Access tokens holds the roles or granted ermissions in it.

 * Authorization with Oauth2 - Flow-1: Safest Flow
    Very secure as once the oauth token is generated, a secure way of sharing access token is done.
    May be it can be with the help of https.


             3rd party service                                                                                Google Account Service
                 |---------------want to access google photos with user account id------------------------------------->|
                 |                                                       |                                              |wait...let me authenticate the user through my auth server and               
                 |                                                       |                                              |ask him if he is ok to give permissions you asked for.  
                 |                                                       |        hey user, can u please tell me        | 
                 |                                                       |if you are ok to give access to google photos |  
                 |                                                       |<---------------------------------------------|
                 |                                                       |                                              | 
                 |                                                       |----------allow him only to view photos------>|
                 |                                                       |                                              |since user trusted and granted permission to access photos
                 |                                                                                                      |
                 |<-----------------------------------------token-------------------------------------------------------| 
                 |        I will let you access the photos as the user is ok with your request.                         | 
                 |        But, only you can just access photos, nothing else allowed.                                   | 
                 |        Sending you the auth token(short lived token) to use for next time as                         | 
                 |        I cannot bother my valuable customer to                                                       | 
                 |        grant permissions everytime you tries to access the photos.                                   | 
                 |                                                                                                      | 
                 |<---------------------------------------------hold this auth token------------------------------------|
                 |                                               but,                                                   |
                 |        would you mid establishing a secure connection if I am generating an acces token from         | 
                 |        auth token and use that further between us. If you are ok with it, share the auth token       |  
                 |           I will send you acces token generated by it                                                |  
                 |                                                                                                      |     
                 |---------------I am ok...sending the auth token back to you------------------------------------------>|
                 |                                                                                                      | 
                 |<------------------------hey...hold this access token...we can talk using it now ownwards-------------|
                 |                                                                                                      | 
                 |----------------------------new requests (JWT access token in header)-------------------------------->|
                 |                                                                                                      |
                 |<-----permits access for valid access token-----------------------------------------------------------|     

 * OAuth Flow 2 - Implicit Flow: - less secure
    The difference is, in this flow, there is no auth token sharing and then generating access token from it 
    when client is sending the auth token back to resource server/ oauth server.
    Here, directly oauth server associated with resource server generates and gives access token.
    Useful when short/ temporary access requests are coming from Java Script. From web pages etc where short-lived 
    access tokens can be shared which has short expiry. 
    Drawback:
    Since directly sharing access token, which is the only thing needed to access resources, if somebody steals, it can hack security.


            
               3rd party service                                                                                Google Account Service
                   |---------------want to access google photos with user account id------------------------------------->|
                   |                                                       |                                              |wait...let me authenticate the user through my auth server and               
                   |                                                       |                                              |ask him if he is ok to give permissions you asked for.  
                   |                                                       |        hey user, can u please tell me        | 
                   |                                                       |if you are ok to give access to google photos |  
                   |                                                       |<---------------------------------------------|
                   |                                                       |                                              | 
                   |                                                       |----------allow him only to view photos------>|
                   |                                                       |                                              |since user trusted and granted permission to access photos
                   |                                                                                                      |
                   |<-----------------------------------------token-------------------------------------------------------| 
                   |        I will let you access the photos as the user is ok with your request.                         | 
                   |        But, only you can just access photos, nothing else allowed.                                   | 
                   |        Sending you the access token to use for next time as I cannot bother my valuable              | 
                   |                                   customer to                                                        | 
                   |        grant permissions everytime you tries to access the photos.                                   | 
                   |                         (difference is , there is no OAuth token, but directly gives access token)   | 
                   |                                                                                                      | 
                   |<------------------------hey...hold this access token...we can talk using it now ownwards-------------|
                   |                                                                                                      | 
                   |----------------------------new requests (JWT access token in header)-------------------------------->|
                   |                                                                                                      |OAuth server validated this access token
                   |<-----permits access for valid access token-----------------------------------------------------------|     


 * OAuth Flow 3 - Client Credential Flow: Widely used in MICROSERVICES
        To use between trusted services.
        To use between our own trust-worthy microservices.
   
        
        
               our own micro service 1(user service)                 Auth Server                                               Payrole micro service
               |           I am sending my client id                   |
               |---------------please provide an access token to me--->|
               |                                                       |
               |                                                       |
               |<----------here is your access token. You will get-----|
               |   things allowed to be used as per your permissions   |
               |   and defined set of roles.                           |
               | You can interact with any other microservice with     |
               | this token. If the API you tries to access allowes    |
               | any of your granted permissions, you will get a       |
               | valid response from them.                             |
               |                                                       |                                              |       
               |-------------------------------------------------------)--->----------------------------------------->|                               
               |                                                       |        hey user, can u please tell me        | 
               |                                                       |                                              |wait...let me check with oauth server       
               |                                                       |<------validate access token of ms1-----------|if you have authority to access my API with your access token. 
               |                                                       |                                              | 
               |                                                       |----yes..ms1 has payroll access allowed------>|
               |                                                       |                                              |since oauth server trusted and granted permission to access my API, go ahead
               |                                                                                                      |
               |<-----------------------------------------valid response from ms2-------------------------------------| 
               |                                                                                                
               |    


 * There is another flow which is used to allow single sign on to one app with another app's credentials.
    Here, it has both authentication and authorization.
   

   * Terminologies:
     
     (Consider scenario - an app printing photos want to access Google Drive photos.)
        1. Resource/ Protected Resource:
            Something to which the access is requested.
           Eg: photos in drive
        2. Resource Owner:
            Someone has ownership to the resource.
           An entity capable of granting access to the protected resource.
           Eg: user
        3. Resource Server:
            Where the protected resource is stored
           Eg: Google Drive
        4. Client:
            App which need to access the protected resource with the permission of resource owner.
           Eg: printing app
        5. AuthOrization Server:
            The resource server is holding the resource. He is liable to ensure security of protected resource.
            This security is handled by OAuth server associated with Google Drive in this scenario.
            Authorization server issues the access tokens to client.
           
           
 * #### People made OAuth enhanced to use it for authentication along with authorization.
    This is called 
   ### Single Sign On:

   1. Create a spring-boot application with 
    

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security.oauth.boot</groupId>
            <artifactId>spring-security-oauth2-autoconfigure</artifactId>
            <version>2.4.5</version>
        </dependency>
 
   2. Set up an app in facebook to access from this spring-boot app for authentication.  
        1. Login into https://developers.facebook.com/
        2. Register your account
        3. Go to My Apps -> create app -> Give app name(app id) -> create
        4. In App details page, Settings-> Basic -> Can use app id as client id and secret as client secret.
           Sample application.yaml
           
                
            security:
                oauth2:
                  client:
                    clientId: <APP_ID>
                    clientSecret: <APP_SECRET>>
                    accessTokenUri: https://graph.facebook.com/oauth/access_token
                    userAuthorizationUri: https://www.facebook.com/dialog/oauth
                    tokenName: oauth_token
                    authenticationScheme: query
                    clientAuthenticationScheme: form
                  resource:
                    userInfoUri: https://graph.facebook.com/me
                                                                  
         
---        then
        * Add An Index page index.html in static folder of resource path.
        * Start app
        * localhost:8080
        * It will redirect to facebook login
        * On success, lands in our index.html or any controller of we gave.

   * With Github
        1. Login to github
        2. Settings-> developer settings->Oauth app
        3. Register new app (url: http://localhost:8080, callback url: http://localhost:8080)
        4. We will get client id and secret for this app.
    
The token details kept in security context.
        
From Spring Security 5, @EnableOauth2Sso is depricated.
Use;

1. Add to pom.xml
        

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-oauth2-client</artifactId>
        </dependency>

2. Configure yaml file
                   

            spring:
                security:
                    oauth2:
                        client:
                            registration:
                                github:
                                    clientId: <GIT APP ID>
                                    clientSecret: <GIT APP SECRET>>

3. Define web security config
            
        
        @EnableWebSecurity
        public class SecurityConfig extends WebSecurityConfigurerAdapter {
                @Override
                protected void configure(HttpSecurity http) throws Exception {
                    http.authorizeRequests(a->a.antMatchers("/error").permitAll()
                    .anyRequest().authenticated())
                    .oauth2Login();
                }
        }
4. Setup index.html
5. Start app
6. Hit localhost:8080
7. Note: In APP config, ensure to set callback url as <b>http://localhost:8080/login/oauth2/code/github</b>
        
Drawback of SSO:
    1. Each time we have to do the validation with credentials as the app check sif currently user is logged in into resource server.

 

Reference:
1. https://www.codejava.net/frameworks/spring-boot/oauth2-login-with-github-example
2. https://www.youtube.com/watch?v=CWiwpvpCrro
3. https://spring.io/guides/tutorials/spring-boot-oauth2/
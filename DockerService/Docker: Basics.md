## Docker

1. To get rid of problems due to dependency version differences, OS chnages etc.
2. Solution for <b>Way to ship my app with my environment also</b>
3. One way of solving issues due to enviroment differences is hypervisors.
4. <b>Hypervisors</b>:
    are virtual machines monitoring mechanism which helps to setup virtual machines on top of native OS where we can install another OS and deploy apps in it.
    We can send an <b>image</b> of a VM with our software installed in it.
   One who uses it can take the image and create an instance of it.
5. But the disadvantage of it is, 
    Resource wastage.
    For each of the hypervisor instance used, need to have it's own OS installed.
    This wastes memory, CPU and hardware.
6. It will be great if you can have a feature which can work as hyperwisers but on top of shared OS.
7. That is where <b>containers</b> came into help
8.  <b>Containers</b>: 
    Type of hypervisor. But using a <b>single shared OS</b> for all the instances. 
    This single shared OS will be the actual native OS itself.
    But all other things like network, apps, depdndency libraries etc will be solely ownded by each container.
9. <b>Docker</b> 
    * is one of such containers
    * Docker is from Docker Inc company and it has free and enterprise editions available
    * Version 20.x
    
    1. Doker Images:
        Can be considered as a source from which the real containers are build.
        Can assume like a class from which we can build multiple containers of same type called instances 
    2. Docker Hub:
        Has all possible docker images
    3. Install with get-dcoker.sh file or as desktop version
    4. There will be a docker daemon underneath to manage installed docker and containers.
    5. <b>docker run hello-world</b>:
    
              What is happending is;
                
              user typed in console----------------->docker client--------------------contacts-------------------->docker daemon
                user see result <-----------------    |                                                              |
                                                      |                                                              |
                                                      |                                                              v
                                                      |                                                   checks for an image requested
                                                      ^                                                              |
                                                      |                                                              |
                                                      |                                                              v
                                                      |                                             if found, create a container form the image
                                                      |                                                              |
                                                      |            stream the output of container to docker client   |
                                                      |--------------------<-----------------------------------------|
                                                      
    6. Docker stores images downloaded in local.
    7. <b>docker image ls</b> or <b>docker images</b>
        List out all images available locally.
    8. <b>docker ps -a</b>
        Lists out all the containers
        
    9. Removing an image frm our local repo:
        * Remove all containers running from this image
        * docker rm {container id}   
        * Now try
        * docker rmi {image id}
    10. Get a new image and use it:
        * <i>pull</i>
        * docker image pull {imagename:tag}    Eg: docker pull ubuntu:latest  
        * Instead of latest, we can add specific version also
        * Now
        * docker images
        * lists out ubuntu also. It is light weight as it holds only basic things for the Ubuntu OS
        * Now <i>run</i> docker container from it
        * docker run {image-id}
        * If we want to execute it in current console (-it = in this console)
        * Append -it, ie;
        * docker run {image-id} -it
        * If we want to run a specific command inside docker container once it is started specify that as well.
        * Eg: IF we want to open bash console once ubuntu startsin this console
        * docker run {image-id} -it /bin/bash
    11. How to create a docker image?
        1. Create Dockerfile
        2. vi Dockerfile
        3. Add instructions to get image, run commands etc.
              
                FROM ubuntu
                MAINTAINER asha
                RUN apt-get update
                RUN apt-get install -y iputils-ping
                CMD ["echo", "hello world"]
                RUN ping www.google.com -c 3 
        4. Above commands will build an ubuntu image having ping installed in it.
        5. docker build -t my-ubuntu:1.0 .
        6. in the same folder of dockerfile will build an image with name tag(-t) my-ubuntu and version 1.0
        
    12. Docker Architecture:
        
            
            Key part is docker engine.
            It manages everything, like images, containers etc.
            Docker Engine - consists of docker client, docker daemon and rest
            It has 3 parts
       
            * Docker Client - outermost layer of docker engine to which user/ outside world interacts
            * REST APIs: Used by docker client to send commands like RUN, CMD etc coming from external world to the docker daemon
            * Docker Daemon: Most powerful core part of docker engine which does evrything as per the request from docker client. Download of images etc is done by this part ofdocker engine.
        
              Our OS-------------------------------------------------
                |     
                |     docker engine---------------------------------------------------------------------------------------- 
                |       |                                                                                                 |
                |       |       ------------------------------------------------                                          |
                |       |       |  docker client                                                                          |
            user--------------->|        |--------------->-via REST calls         |                                                |
                |       |       |                                                 |                                       |
                |       |                                                         |------------>Docker Daemon   --------------------contacts------->containerd (the                 |       |                                                                                                                         real container                   |       |                                                                                                                      daemon which                         |       |                                                                                                                                 manages                   |       |                                                                                                                     containerlifecycles) 
                |       |                                                                                                 |                           |    | 
                |       |                                                                                                 |                           |                             |       |                                                                                                                               |contacts
                |       |                                                                                                 |                           |    v 
                |       |--------------------------------------------------------------------------------------------------                           |    runc                                                                                                                                                                 (which                                                                                                                                              is actually managing                                                                                                                                                the individual                                                                                                                                                  container and its life                                                                                                                                                 |             cycle)
                                                                                                                                            |          |
                                                                                                                                            |          v                                                                                                                                                       | 
                                                                                                                                            |         runc for                                                                                                                                                 |         container 1                                                                                                                                               |
                                                                                                                                            v
                                                                                                                                                                                                                                                                                                            runc for container n
                                                                                                                                                                                    
                                                                                                                                                                                    
                                                                                                                                                                                    
                                                                                                                                                                                    
                                                                                                                                                                                    
13. Storage Volumes:
      
            The data needs to be stored somewhere for our apps runnng in containers.
            <b><u>Overlay2</u></b> is a mechanism to store data from containers.

      14. Containerize Apps 2 ways:
            Write app inside container itself
            or
            Write and dockerize app
          
14. Create Springboot App And Dockerize:
    1. Create springboot app
    2. Create a "Dockerfile" in same level of pom.xml
    3. Add
       
            FROM openjdk:16 ------------------get base image from docker hub for Java
            ADD target/DockerService-1.0-SNAPSHOT.jar DockerService-1.0-SNAPSHOT.jar --------ADD jar file generated by maven build to the base path in container as DockerService-1.0-SNAPSHOT.jar
            EXPOSE 8085                         ------------------------------------------open port 8085 of docker. This is private port of container. This is just like we set 8085 of our normal OS ports. But it will be only accessible within the container. Ensure server.port also is 8085.
            ENTRYPOINT ["java", "-jar", "DockerService-1.0-SNAPSHOT.jar"]  ---------------------- Command to start the application
    4. Run <b>docker build -t first-docker-app:1.0 .</b> in project folder. Same location of pom.xml. -t means tag which is name of image.
    5. docker images --------lists images in local
    6. <b>docker run -p 8085:8085 first-docker-app:1.0</b>   ---------will start the container running our app.
    7. If required to rebuild image, <b>docker build -t first-docker-app:1.0 .</b> can be used.

14. Push Docker Image to Docker Hub:
    1. Create an account in docker hub
    2. Type <b>docker login</b> in console
    3. Give credential(docker hub id, password) for docker hub account
    4. Prepare a tag from existing image build
        docker tag imagename dockerhub_id/imgname:version ---------(docker tag first-springboot-docker-app:1.0 manikuttyselffav/first-springboot-app )
    5. It will tag and gives a new image
    6. docker push dockerhub_id/imgname:version -----------(docker push manikuttyselffav/first-springboot-app)
    7. To download it to local; docker pull dockerhub_id/imgname:version -----------(docker pull manikuttyselffav/first-springboot-app)
    8. docker run -p 8085:8085 manikuttyselffav/first-springboot-app -----------will run the container
15. Using Spotify Maven Plugin To Push Docker Image:
    1. Add plugin to pom.xml as below
            
                
        <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>2.0.1.RELEASE</version>
            </plugin>
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>dockerfile-maven-plugin</artifactId>
                <version>1.4.13</version>
                <executions>
                    <execution>
                        <id>default</id>
                        <phase>install</phase><!--Remove install phase if local push is enogh-->
                        <goals>
                            <goal>build</goal>
                            <goal>push</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <!--<repository>${project.artifactId}</repository> enable this if local repo installation is to be done. On removing it, an image with user-id/reponame will get created.-->
                    <repository>manikuttyselffav/${project.artifactId}</repository>
                    <tag>${project.version}</tag>
                    <buildArgs>
                        <JAR_FILE>target/${project.build.finalName}.jar</JAR_FILE>
                    </buildArgs>
                    <useMavenSettingsForAuth>true</useMavenSettingsForAuth><!--Use this to enable credentials to be taken from m2/settings.xml file for docker.io-->
                </configuration>
            </plugin>
        </plugins>
        <finalName>maven-docker-app</finalName> ---indicates final jar name
    </build>

   2. Add docker.io details and credentials in maven settings.xml

                
                <?xml version="1.0" encoding="UTF-8"?>
                <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                https://maven.apache.org/xsd/settings-1.0.0.xsd">
                <localRepository/>
                <interactiveMode/>
                <usePluginRegistry/>
                <offline/>
                <pluginGroups/>
                <servers>
                <server>
                <id>docker.io</id>
                <username>docker-hub-id</username>
                <password>password</password>
                </server>
                </servers>
                <mirrors/>
                <proxies/>
                <profiles/>
                <activeProfiles/>
                </settings>
   3. Perform mvn clean install

16. spring-boot-maven Build Plugin 2.3.x with new goal for "build-image":
    * mvn clean spring-boot:build-image
17. Integrate With Jenkins:
    1. ###Jenkins:
        * open source build process automation tool
        * 
    

    

    
    
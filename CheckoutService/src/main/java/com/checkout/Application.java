package com.checkout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"autoconfig", "com.checkout"})//if only one package defined here, default is not included.
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

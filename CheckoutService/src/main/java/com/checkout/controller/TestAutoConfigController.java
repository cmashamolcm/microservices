package com.checkout.controller;

import autoconfig.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAutoConfigController {

    @Autowired
    TestConfig testConfig;

    @GetMapping()
    public String testConfig(){
        if (testConfig == null){
            return "Test config is null";
        }
        return "Test config is not null";
    }
}

package com.checkout.controller;

import autoconfig.TestConfig;
import com.checkout.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAutoConfigController {

    @Autowired
    TestConfig testConfig;

    TestService testService;

    public TestAutoConfigController(TestService testService){
            this.testService = testService;
    }

    @GetMapping()
    public String testConfig(){
        testService.testJava8();
        if (testConfig == null){
            return "Test config is null";
        }
        return "Test config is not null";
    }
}

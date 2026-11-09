package com.example.hello_world;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World from AWS Spring Boot! with Auto deploy using github actions with OIDC";
    }
    @GetMapping("/test")
    public String hello() {
        return "Hello test from AWS Spring Boot";
    }
}
package com.example.expensemanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World from AWS Spring Boot! with Auto deploy using github actions with OIDC";
    }
    @GetMapping("/test")
    public String test() {
        return "Hello test from AWS Spring Boot";
    }
    @GetMapping("/addtest")
    public String addingtest() {
        return "Hello test from AWS in Spring Boot";
    }
}
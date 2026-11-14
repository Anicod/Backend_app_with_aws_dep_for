package com.example.expensemanagement.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

    @Value("${app.environment:UNKNOWN}")
    private String environment;

    @Value("${app.message:Hello World}")
    private String message;

    @GetMapping("/profile")
    public Map<String, String> getProfile() {
        return Map.of(
                "environment", environment,
                "message", message
        );
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
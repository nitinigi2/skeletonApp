package org.example.controller;

import org.example.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Greeting sayHello() {
        return new Greeting("Hello, World!");
    }
}

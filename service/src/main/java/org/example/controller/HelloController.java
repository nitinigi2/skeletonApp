package org.example.controller;

import org.example.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping(value = "/hello")
    public ResponseEntity getHelloMessage() {
        String responseBody = helloService.getHelloWorldMessage();
        return ResponseEntity.ok().body(responseBody);
    }
}

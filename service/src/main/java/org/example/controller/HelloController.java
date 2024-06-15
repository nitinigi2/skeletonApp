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

    @GetMapping(value = "/public")
    public ResponseEntity<String> getHelloMessage() {
        String responseBody = helloService.getHelloWorldMessage();
        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/secured")
    public ResponseEntity<String> sayHelloToAdmin() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }
}

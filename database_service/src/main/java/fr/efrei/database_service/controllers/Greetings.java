package fr.efrei.database_service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Greetings {

    @GetMapping("/greetings")
    public String getGreetings() {
        return "Hello, world!";
    }
}


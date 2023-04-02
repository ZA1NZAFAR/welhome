package fr.efrei.database_service.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

    @GetMapping("/greetings")
    public String getGreetings() {
        return "Hiiiii \uD83E\uDEF6 \uD83E\uDEF0 !";
    }
}


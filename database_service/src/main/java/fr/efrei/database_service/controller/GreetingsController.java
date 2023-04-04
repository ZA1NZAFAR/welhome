package fr.efrei.database_service.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Greetings", description = "You want to say hi ?")
public class GreetingsController {

    @GetMapping("/greetings")
    @Operation(summary = "Say hi to the world")
    public String getGreetings() {
        return "Hiiiii \uD83E\uDEF6 \uD83E\uDEF0 !";
    }
}


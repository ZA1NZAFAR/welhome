package fr.efrei.backend.controllers;

import fr.efrei.backend.entities.Profile;
import fr.efrei.backend.utils.ResponseGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class ProfilesController {
    @Value("${databaseService.url}/profiles")
    private String URL;

    @GetMapping("/api/profiles")
    public ResponseEntity<Profile[]> all() {
        ResponseGenerator<Profile[]> generator = new ResponseGenerator<>();
        ResponseEntity<Profile[]> result = generator.execute(URL, HttpMethod.GET, new ParameterizedTypeReference<Profile[]>() {});
        return result;
    }

    @GetMapping("/api/profiles/{email}")
    public ResponseEntity<Profile> one(@PathVariable String email) {
        ResponseGenerator<Profile> generator = new ResponseGenerator<>();
        ResponseEntity<Profile> result = generator.execute(URL.concat("/" + email), HttpMethod.GET, new ParameterizedTypeReference<Profile>() {});
        return result;
    }
}

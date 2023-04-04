package fr.efrei.backend.controllers;

import fr.efrei.backend.entities.Profile;
import fr.efrei.backend.utils.ResponseGenerator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class ProfilesController {
    @Value("${databaseService.url}/profiles")
    private String URL;

    @GetMapping("/api/profiles")
    public ResponseEntity<Profile[]> getUsers() {
        ResponseGenerator<Profile[]> generator = new ResponseGenerator<>();
        ResponseEntity<Profile[]> result = generator.buildRequest(URL, HttpMethod.GET, new ParameterizedTypeReference<Profile[]>() {});
        return result;
    }

    @GetMapping("/api/profiles/{email}")
    public ResponseEntity<Profile> getUser(@PathVariable String email) {
        ResponseGenerator<Profile> generator = new ResponseGenerator<>();
        ResponseEntity<Profile> result = generator.buildRequest(URL.concat("/" + email), HttpMethod.GET, new ParameterizedTypeReference<Profile>() {});
        return result;
    }

    @PostMapping("/api/profiles")
    public ResponseEntity<Profile> postUser(@RequestBody Profile user) {
        ResponseGenerator<Profile> generator = new ResponseGenerator<>();
        ResponseEntity<Profile> result = generator.buildRequest(URL, HttpMethod.POST, user, new ParameterizedTypeReference<Profile>() {});
        return result;
    }

    @PutMapping("/api/profiles/{email}")
    public ResponseEntity<Profile> putUser(@PathVariable String email, @RequestBody Profile user) {
        ResponseGenerator<Profile> generator = new ResponseGenerator<>();
        ResponseEntity<Profile> result = generator.buildRequest(URL.concat("/" + email), HttpMethod.PUT, user, new ParameterizedTypeReference<Profile>() {});
        return result;
    }

    @DeleteMapping("/api/profiles/{email}")
    public ResponseEntity<Profile> deleteUser(@PathVariable String email) {
        ResponseGenerator<Profile> generator = new ResponseGenerator<>();
        ResponseEntity<Profile> result = generator.buildRequest(URL.concat("/" + email), HttpMethod.DELETE, new ParameterizedTypeReference<Profile>() {});
        return result;
    }
}

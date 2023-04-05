package fr.efrei.backend.controllers;

import fr.efrei.backend.entities.Profile;
import fr.efrei.backend.utils.ResponseGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProfilesController {
    @Value("${databaseService.url}/profiles")
    private String URL;

    @GetMapping("/api/profiles")
    public ResponseEntity<List<Profile>> getUsers() {
        ResponseGenerator<List<Profile>> generator = new ResponseGenerator<>();
        ResponseEntity<List<Profile>> result = generator.buildRequest(URL, HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
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

    @GetMapping("/api/profiles/firstName/{firstName}")
    public ResponseEntity<List<Profile>> getUserByFirstName(@PathVariable String firstName) {
        ResponseGenerator<List<Profile>> generator = new ResponseGenerator<>();
        ResponseEntity<List<Profile>> result = generator.buildRequest(URL.concat("/firstName/" + firstName), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/api/profiles/lastName/{lastName}")
    public ResponseEntity<List<Profile>> getUserByLastName(@PathVariable String lastName) {
        ResponseGenerator<List<Profile>> generator = new ResponseGenerator<>();
        ResponseEntity<List<Profile>> result = generator.buildRequest(URL.concat("/lastName/" + lastName), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/api/profiles/birthDate/{birthDate}")
    public ResponseEntity<List<Profile>> getUserByBirthDate(@PathVariable String birthDate) {
        ResponseGenerator<List<Profile>> generator = new ResponseGenerator<>();
        ResponseEntity<List<Profile>> result = generator.buildRequest(URL.concat("/birthDate/" + birthDate), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/api/profiles/phoneNumber/{phoneNumber}")
    public ResponseEntity<List<Profile>> getUserByPhoneNumber(@PathVariable String phoneNumber) {
        ResponseGenerator<List<Profile>> generator = new ResponseGenerator<>();
        ResponseEntity<List<Profile>> result = generator.buildRequest(URL.concat("/phoneNumber/" + phoneNumber), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/api/profiles/gender/{gender}")
    public ResponseEntity<List<Profile>> getUserByGender(@PathVariable String gender) {
        ResponseGenerator<List<Profile>> generator = new ResponseGenerator<>();
        ResponseEntity<List<Profile>> result = generator.buildRequest(URL.concat("/gender/" + gender), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/api/profiles/birthDateBetween")
    public ResponseEntity<List<Profile>> getUserByBirthDateBetween(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        ResponseGenerator<List<Profile>> generator = new ResponseGenerator<>();
        ResponseEntity<List<Profile>> result = generator.buildRequest(URL.concat("/birthDateBetween/" + startDate + "/" + endDate), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }
}

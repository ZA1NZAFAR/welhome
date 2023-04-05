package fr.efrei.backend.controllers;

import fr.efrei.backend.entities.Profile;
import fr.efrei.backend.utils.ResponseGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProfilesController {
    @Value("${databaseService.url}/profiles")
    private String URL;

    // Singleton instances of ResponseGenerator
    private ResponseGenerator<List<Profile>> listGenerator;         // to send out JSON array in Response
    private ResponseGenerator<Profile> generator;                   // to send out JSON object in Response

    @PostConstruct
    @Autowired
    private void init() {
        listGenerator = new ResponseGenerator<>();
        generator = new ResponseGenerator<>();
    }

    @GetMapping("/api/profiles")
    public ResponseEntity<List<Profile>> getUsers() {
        ResponseEntity<List<Profile>> result = listGenerator.buildRequest(URL, HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/api/profiles/{email}")
    public ResponseEntity<Profile> getUser(@PathVariable String email) {
        ResponseEntity<Profile> result = generator.buildRequest(URL.concat("/" + email), HttpMethod.GET, new ParameterizedTypeReference<Profile>() {});
        return result;
    }

    @PostMapping("/api/profiles")
    public ResponseEntity<Profile> postUser(@RequestBody Profile user) {
        ResponseEntity<Profile> result = generator.buildRequest(URL, HttpMethod.POST, user, new ParameterizedTypeReference<Profile>() {});
        return result;
    }

    @PutMapping("/api/profiles/{email}")
    public ResponseEntity<Profile> putUser(@PathVariable String email, @RequestBody Profile user) {
        ResponseEntity<Profile> result = generator.buildRequest(URL.concat("/" + email), HttpMethod.PUT, user, new ParameterizedTypeReference<Profile>() {});
        return result;
    }

    @DeleteMapping("/api/profiles/{email}")
    public ResponseEntity<Profile> deleteUser(@PathVariable String email) {
        ResponseEntity<Profile> result = generator.buildRequest(URL.concat("/" + email), HttpMethod.DELETE, new ParameterizedTypeReference<Profile>() {});
        return result;
    }

    @GetMapping("/api/profiles/firstName/{firstName}")
    public ResponseEntity<List<Profile>> getUserByFirstName(@PathVariable String firstName) {
        ResponseEntity<List<Profile>> result = listGenerator.buildRequest(URL.concat("/firstName/" + firstName), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/api/profiles/lastName/{lastName}")
    public ResponseEntity<List<Profile>> getUserByLastName(@PathVariable String lastName) {
        ResponseEntity<List<Profile>> result = listGenerator.buildRequest(URL.concat("/lastName/" + lastName), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/api/profiles/birthDate/{birthDate}")
    public ResponseEntity<List<Profile>> getUserByBirthDate(@PathVariable String birthDate) {
        ResponseEntity<List<Profile>> result = listGenerator.buildRequest(URL.concat("/birthDate/" + birthDate), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/api/profiles/phoneNumber/{phoneNumber}")
    public ResponseEntity<List<Profile>> getUserByPhoneNumber(@PathVariable String phoneNumber) {
        ResponseEntity<List<Profile>> result = listGenerator.buildRequest(URL.concat("/phoneNumber/" + phoneNumber), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/api/profiles/gender/{gender}")
    public ResponseEntity<List<Profile>> getUserByGender(@PathVariable String gender) {
        ResponseEntity<List<Profile>> result = listGenerator.buildRequest(URL.concat("/gender/" + gender), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/api/profiles/birthDateBetween")
    public ResponseEntity<List<Profile>> getUserByBirthDateBetween(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        ResponseEntity<List<Profile>> result = listGenerator.buildRequest(URL.concat("/birthDateBetween/" + startDate + "/" + endDate), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }
}

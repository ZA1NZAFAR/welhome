package fr.efrei.backend.controllers;

import fr.efrei.backend.entities.Profile;
import fr.efrei.backend.utils.ResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "Profiles", description = "This API allows to perform a set of tasks related to the user profile: renter & owner")
@RequestMapping("/api/profiles")
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

    @GetMapping
    @Operation(summary = "This endpoint allows to retrieve all user profiles")
    public ResponseEntity<List<Profile>> getUsers() {
        ResponseEntity<List<Profile>> result = listGenerator.buildRequest(URL, HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/{email}")
    @Operation(summary = "This endpoint allows to retrieve a user profile based on email")
    public ResponseEntity<Profile> getUser(@PathVariable String email) {
        ResponseEntity<Profile> result = generator.buildRequest(URL.concat("/" + email), HttpMethod.GET, new ParameterizedTypeReference<Profile>() {});
        return result;
    }

    @PostMapping
    @Operation(summary = "This endpoint creates a new user profile")
    @ApiResponse(responseCode = "201", description = "Creates a user profile")
    public ResponseEntity<Profile> postUser(@RequestBody Profile user) {
        ResponseEntity<Profile> result = generator.buildRequest(URL, HttpMethod.POST, user, new ParameterizedTypeReference<Profile>() {});
        return result;
    }

    @PutMapping("/{email}")
    @Operation(summary = "This endpoint allows to update a user profile based on email if exists, if not it creates a new user profile")
    @ApiResponse(responseCode = "200", description = "Updates a user profile")
    @ApiResponse(responseCode = "201", description = "Creates a user profile")
    public ResponseEntity<Profile> putUser(@PathVariable String email, @RequestBody Profile user) {
        ResponseEntity<Profile> result = generator.buildRequest(URL.concat("/" + email), HttpMethod.PUT, user, new ParameterizedTypeReference<Profile>() {});
        return result;
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "This endpoint allows to delete a user profile based on email")
    @ApiResponse(responseCode = "200", description = "Deletes a user profile")
    @ApiResponse(responseCode = "404", description = "User profile does not exist")
    public ResponseEntity<Profile> deleteUser(@PathVariable String email) {
        ResponseEntity<Profile> result = generator.buildRequest(URL.concat("/" + email), HttpMethod.DELETE, new ParameterizedTypeReference<Profile>() {});
        return result;
    }

    @GetMapping("/first_name/{firstName}")
    @Operation(summary = "This endpoint allows to retrieve a user profile based on first name")
    public ResponseEntity<List<Profile>> getUserByFirstName(@PathVariable String firstName) {
        ResponseEntity<List<Profile>> result = listGenerator.buildRequest(URL.concat("/firstName/" + firstName), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/last_name/{lastName}")
    @Operation(summary = "This endpoint allows to retrieve a user profile based on last name")
    public ResponseEntity<List<Profile>> getUserByLastName(@PathVariable String lastName) {
        ResponseEntity<List<Profile>> result = listGenerator.buildRequest(URL.concat("/lastName/" + lastName), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/birth_date/{birthDate}")
    @Operation(summary = "This endpoint allows to retrieve a user profile based on birth date")
    public ResponseEntity<List<Profile>> getUserByBirthDate(@PathVariable String birthDate) {
        ResponseEntity<List<Profile>> result = listGenerator.buildRequest(URL.concat("/birthDate/" + birthDate), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/phone_number/{phoneNumber}")
    @Operation(summary = "This endpoint allows to retrieve a user profile based on phone number")
    public ResponseEntity<List<Profile>> getUserByPhoneNumber(@PathVariable String phoneNumber) {
        ResponseEntity<List<Profile>> result = listGenerator.buildRequest(URL.concat("/phoneNumber/" + phoneNumber), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/gender/{gender}")
    @Operation(summary = "This endpoint allows to retrieve a user profile based on gender")
    public ResponseEntity<List<Profile>> getUserByGender(@PathVariable String gender) {
        ResponseEntity<List<Profile>> result = listGenerator.buildRequest(URL.concat("/gender/" + gender), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }

    @GetMapping("/birth_date_between")
    @Operation(summary = "This endpoint allows to retrieve a user profile whose birth date is between 2 given dates")
    @ApiResponse(responseCode = "200", description = "User profile is found")
    @ApiResponse(responseCode = "404", description = "User profile has not been found")
    public ResponseEntity<List<Profile>> getUserByBirthDateBetween(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        ResponseEntity<List<Profile>> result = listGenerator.buildRequest(URL.concat("/birthDateBetween/" + startDate + "/" + endDate), HttpMethod.GET, new ParameterizedTypeReference<List<Profile>>() {});
        return result;
    }
}

package fr.efrei.database_service.controller;

import fr.efrei.database_service.dto.ProfileDTO;
import fr.efrei.database_service.entity.ProfileEntity;
import fr.efrei.database_service.exception.DatabaseExceptions;
import fr.efrei.database_service.service.ProfileService;
import fr.efrei.database_service.tools.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Profiles", description = "This API will allow us to perform a set of tasks related to the user profile: renter & owner")
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping
    @Operation(summary = "This endpoint will create a new user profile")
    public ResponseEntity<?> createUser(@RequestBody ProfileDTO user) {
        ProfileEntity userToCreate;
        try {
            userToCreate = profileService.save(Mapper.convert(user, ProfileEntity.class));
        } catch (DatabaseExceptions.EntityAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        }
        return ResponseEntity.ok(Mapper.convert(userToCreate, ProfileDTO.class));
    }


    @GetMapping("/{email}")
    @Operation(summary = "This endpoint will allow to retrieve a user profile based on email")
    public ResponseEntity<?> getUser(@PathVariable String email) {
        ProfileEntity user;
        try {
            user = this.profileService.findById(email);
        } catch (DatabaseExceptions.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Mapper.convert(user, ProfileDTO.class));
    }

    @PutMapping("/{email}")
    @Operation(summary = "This endpoint will allow to update a user profile based on email")
    public ResponseEntity<?> updateUser(@PathVariable String email, @RequestBody ProfileDTO profileDTO) {
        try {
            ProfileEntity updatedProfile = profileService.update(email, Mapper.convert(profileDTO, ProfileEntity.class));
            return ResponseEntity.ok(Mapper.convert(updatedProfile, ProfileDTO.class));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "This endpoint will allow to delete a user profile based on email")
    public ResponseEntity<ProfileDTO> deleteUser(@PathVariable String email) {
        try {
            this.profileService.deleteById(email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "This endpoint will allow to retrieve all users profiles")
    public List<ProfileDTO> getAllUsers() {
        return this.profileService.findAll().stream().map(user -> Mapper.convert(user, ProfileDTO.class)).collect(Collectors.toList());
    }


    @GetMapping("/lastName/{lastName}")
    @Operation(summary = "This endpoint will allow to retrieve a user profile based on last name")
    public List<ProfileDTO> getUserByLastName(@PathVariable String lastName) {
        return this.profileService.findByLastName(lastName).stream().map(user -> Mapper.convert(user, ProfileDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/firstName/{firstName}")
    @Operation(summary = "This endpoint will allow to retrieve a user profile based on first name")
    public List<ProfileDTO> getUserByFirstName(@PathVariable String firstName) {
        return this.profileService.findByFirstName(firstName).stream().map(user -> Mapper.convert(user, ProfileDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    @Operation(summary = "This endpoint will allow to retrieve a user profile based on phone number")
    public List<ProfileDTO> getUserByPhoneNumber(@PathVariable String phoneNumber) {
        return this.profileService.findByPhoneNumber(phoneNumber).stream().map(user -> Mapper.convert(user, ProfileDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/birthDate/{birthDate}")
    @Operation(summary = "This endpoint will allow to retrieve a user profile based on birth date")
    public List<ProfileDTO> getUserByBirthDate(@PathVariable Date birthDate) {
        return this.profileService.findByBirthDate(birthDate).stream().map(user -> Mapper.convert(user, ProfileDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/birthDateBetween/{birthDate1}/{birthDate2}")
    @Operation(summary = "this endpoint will allow to retrive a user profile whose birth date is between 2 dates")
    public List<ProfileDTO> getUserByBirthDateBetween(@PathVariable Date birthDate1, @PathVariable Date birthDate2) {
        return this.profileService.findByBirthDateBetween(birthDate1, birthDate2).stream().map(user -> Mapper.convert(user, ProfileDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/gender/{gender}")
    @Operation(summary = "This endpoint will allow to retrieve a user profile based on gender")
    public List<ProfileDTO> getUserByGender(@PathVariable String gender) {
        return this.profileService.findByGender(gender).stream().map(user -> Mapper.convert(user, ProfileDTO.class)).collect(Collectors.toList());
    }
}

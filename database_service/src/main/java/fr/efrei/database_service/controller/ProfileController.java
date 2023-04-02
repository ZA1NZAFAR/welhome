package fr.efrei.database_service.controller;

import fr.efrei.database_service.dto.ProfileDTO;
import fr.efrei.database_service.entity.ProfileEntity;
import fr.efrei.database_service.service.ProfileService;
import fr.efrei.database_service.tools.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ProfileDTO createUser(@RequestBody ProfileEntity user) {
        return Mapper.convertToDto(this.profileService.save(user), ProfileDTO.class);
    }

    @GetMapping("/{email}")
    @Operation(summary = "This endpoint will allow to retrieve a user profile based on email")
    public ResponseEntity<ProfileDTO> getUser(@PathVariable String email) {
        ProfileEntity user = this.profileService.findById(email);
        if (user == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(Mapper.convertToDto(user, ProfileDTO.class));
    }

    @PutMapping("/{email}")
    @Operation(summary = "This endpoint will allow to update a user profile based on email")
    public ProfileDTO updateUser(@PathVariable String email, @RequestBody ProfileEntity user) {
        return Mapper.convertToDto(profileService.update(email, user), ProfileDTO.class);
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "This endpoint will allow to delete a user profile based on email")
    public void deleteUser(@PathVariable String email) {
        this.profileService.deleteById(email);
    }

    @GetMapping
    @Operation(summary = "This endpoint will allow to retrieve all users profiles")
    public List<ProfileDTO> getAllUsers() {
        return this.profileService.findAll().stream().map(user -> Mapper.convertToDto(user, ProfileDTO.class)).collect(Collectors.toList());
    }


    @GetMapping("/lastName/{lastName}")
    @Operation(summary = "This endpoint will allow to retrieve a user profile based on last name")
    public List<ProfileDTO> getUserByLastName(@PathVariable String lastName) {
        return this.profileService.findByLastName(lastName).stream().map(user -> Mapper.convertToDto(user, ProfileDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/firstName/{firstName}")
    @Operation(summary = "This endpoint will allow to retrieve a user profile based on first name")
    public List<ProfileDTO> getUserByFirstName(@PathVariable String firstName) {
        return this.profileService.findByFirstName(firstName).stream().map(user -> Mapper.convertToDto(user, ProfileDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    @Operation(summary = "This endpoint will allow to retrieve a user profile based on phone number")
    public List<ProfileDTO> getUserByPhoneNumber(@PathVariable String phoneNumber) {
        return this.profileService.findByPhoneNumber(phoneNumber).stream().map(user -> Mapper.convertToDto(user, ProfileDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/birthDate/{birthDate}")
    @Operation(summary = "This endpoint will allow to retrieve a user profile based on birth date")
    public List<ProfileDTO> getUserByBirthDate(@PathVariable Date birthDate) {
        return this.profileService.findByBirthDate(birthDate).stream().map(user -> Mapper.convertToDto(user, ProfileDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/birthDateBetween/{birthDate1}/{birthDate2}")
    @Operation(summary = "this endpoint will allow to retrive a user profile whose birth date is between 2 dates")
    public List<ProfileDTO> getUserByBirthDateBetween(@PathVariable Date birthDate1, @PathVariable Date birthDate2) {
        return this.profileService.findByBirthDateBetween(birthDate1, birthDate2).stream().map(user -> Mapper.convertToDto(user, ProfileDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/gender/{gender}")
    @Operation(summary = "This endpoint will allow to retrieve a user profile based on gender")
    public List<ProfileDTO> getUserByGender(@PathVariable String gender) {
        return this.profileService.findByGender(gender).stream().map(user -> Mapper.convertToDto(user, ProfileDTO.class)).collect(Collectors.toList());
    }
}

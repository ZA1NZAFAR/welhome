package fr.efrei.database_service.controller;

import fr.efrei.database_service.entity.ProfileEntity;
import fr.efrei.database_service.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping
    public ProfileEntity createUser(@RequestBody ProfileEntity user) {
        return this.profileService.save(user);
    }
    @GetMapping("/{email}")
    public ProfileEntity getUser(@PathVariable String email) {
        return this.profileService.findById(email);
    }

    @PutMapping("/{email}")
    public ProfileEntity updateUser(@PathVariable String email, @RequestBody ProfileEntity user) {
        return profileService.update(email, user);
    }

    @DeleteMapping("/{email}")
    public void deleteUser(@PathVariable String email) {
        this.profileService.deleteById(email);
    }

    @GetMapping
    public List<ProfileEntity> getAllUsers() {
        return this.profileService.findAll();
    }


    @GetMapping("/lastName/{lastName}")
    public List<ProfileEntity> getUserByLastName(@PathVariable String lastName) {
        return this.profileService.findByLastName(lastName);
    }

    @GetMapping("/firstName/{firstName}")
    public List<ProfileEntity> getUserByFirstName(@PathVariable String firstName) {
        return this.profileService.findByFirstName(firstName);
    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    public List<ProfileEntity> getUserByPhoneNumber(@PathVariable String phoneNumber) {
        return this.profileService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping("/birthDate/{birthDate}")
    public List<ProfileEntity> getUserByBirthDate(@PathVariable Date birthDate) {
        return this.profileService.findByBirthDate(birthDate);
    }

    @GetMapping("/birthDateBetween/{birthDate1}/{birthDate2}")
    public List<ProfileEntity> getUserByBirthDateBetween(@PathVariable Date birthDate1, @PathVariable Date birthDate2) {
        return this.profileService.findByBirthDateBetween(birthDate1, birthDate2);
    }

    @GetMapping("/gender/{gender}")
    public List<ProfileEntity> getUserByBirthDate(@PathVariable String gender) {
        return this.profileService.findByGender(gender);
    }
}

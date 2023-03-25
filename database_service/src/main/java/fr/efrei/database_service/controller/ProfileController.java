package fr.efrei.database_service.controller;

import fr.efrei.database_service.entity.Profile;
import fr.efrei.database_service.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // get all users
    @GetMapping
    public List<Profile> getAllUsers() {
        return this.profileService.findAll();
    }

    @GetMapping("/{email}")
    public Profile getUser(@PathVariable String email) {
        return this.profileService.findByEmail(email).orElse(null);
    }

    @GetMapping("/lastName/{lastName}")
    public List<Profile> getUserByLastName(@PathVariable String lastName) {
        return this.profileService.findByLastName(lastName);
    }

    @GetMapping("/firstName/{firstName}")
    public List<Profile> getUserByFirstName(@PathVariable String firstName) {
        return this.profileService.findByFirstName(firstName);
    }


    @PostMapping
    public Profile createUser(@RequestBody Profile user) {
        return this.profileService.save(user);
    }

    @DeleteMapping("/{email}")
    public void deleteUser(@PathVariable String email) {
        this.profileService.deleteByEmail(email);
    }

    @PutMapping("/{email}")
    public Profile updateUser(@PathVariable String email, @RequestBody Profile user) {
        return profileService.update(email, user);
    }


    @PostMapping
    public String save(@Valid @RequestBody ProfileVO vO) {
        return profileService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") String id) {
        profileService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") String id,
                       @Valid @RequestBody ProfileUpdateVO vO) {
        profileService.update(id, vO);
    }

    @GetMapping("/{id}")
    public ProfileDTO getById(@Valid @NotNull @PathVariable("id") String id) {
        return profileService.getById(id);
    }

    @GetMapping
    public Page<ProfileDTO> query(@Valid ProfileQueryVO vO) {
        return profileService.query(vO);
    }
}

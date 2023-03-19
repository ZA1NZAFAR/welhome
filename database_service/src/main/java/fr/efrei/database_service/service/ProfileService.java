package fr.efrei.database_service.service;


import fr.efrei.database_service.entity.Profile;
import fr.efrei.database_service.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public Optional<Profile> findByEmail(String email) {
        return profileRepository.findById(email);
    }

    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    public void deleteByEmail(String email) {
        profileRepository.deleteById(email);
    }

    public Profile update(String email, Profile profile) {
        profile.setEmail(email);
        return profileRepository.save(profile);
    }

    public List<Profile> findByLastName(String lastName) {
        return profileRepository.findByLastName(lastName);
    }

    public List<Profile> findByFirstName(String firstName) {
        return profileRepository.findByFirstName(firstName);
    }
}


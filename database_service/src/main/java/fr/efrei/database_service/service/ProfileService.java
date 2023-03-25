package fr.efrei.database_service.service;


import fr.efrei.database_service.entity.ProfileEntity;
import fr.efrei.database_service.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ProfileService implements CRUD<ProfileEntity, String> {

    //CRUD
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public ProfileEntity save(ProfileEntity profile) {
        return profileRepository.save(profile);
    }

    @Override
    public ProfileEntity findById(String id) {
        return profileRepository.findById(id).orElse(null);
    }


    @Override
    public ProfileEntity update(String email, ProfileEntity profile) {
        profile.setEmail(email);
        return profileRepository.save(profile);
    }

    @Override
    public void deleteById(String id) {
        profileRepository.deleteById(id);
    }


    public List<ProfileEntity> findAll() {
        return profileRepository.findAll();
    }

    public List<ProfileEntity> findByLastName(String lastName) {
        return profileRepository.findByLastName(lastName);
    }

    public List<ProfileEntity> findByFirstName(String firstName) {
        return profileRepository.findByFirstName(firstName);
    }

    public List<ProfileEntity> findByPhoneNumber(String phoneNumber) {
        return profileRepository.findByPhoneNumber(phoneNumber);
    }

    public List<ProfileEntity> findByBirthDate(Date birthDate) {
        return profileRepository.findByBirthDate(birthDate);
    }

    public List<ProfileEntity> findByBirthDateBetween(Date birthDate1, Date birthDate2) {
        return profileRepository.findByBirthDateBetween(birthDate1, birthDate2);
    }

    public List<ProfileEntity> findByGender(String gender) {
        return profileRepository.findByGender(gender);
    }

}


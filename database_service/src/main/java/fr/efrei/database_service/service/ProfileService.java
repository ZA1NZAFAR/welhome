package fr.efrei.database_service.service;


import fr.efrei.database_service.entity.ProfileEntity;
import fr.efrei.database_service.exception.DatabaseExceptions;
import fr.efrei.database_service.repository.ProfileRepository;
import fr.efrei.database_service.tools.Tools;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
public class ProfileService implements CRUD<ProfileEntity, String> {

    //CRUD
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public ProfileEntity save(ProfileEntity profile) {
        if (profileRepository.findById(profile.getEmail()).isPresent())
            throw new DatabaseExceptions.EntityAlreadyExistsException();
        if (profile.getRegistrationDate()==null)
            profile.setRegistrationDate(new Date(System.currentTimeMillis()));
        return profileRepository.save(profile);
    }

    @Override
    public ProfileEntity findById(String id) {
        return profileRepository.findById(id).orElseThrow(DatabaseExceptions.EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public ProfileEntity update(String email, ProfileEntity updatedProfile) throws IllegalArgumentException, EntityNotFoundException {
        ProfileEntity existingProfile = profileRepository.findById(email).orElseThrow(EntityNotFoundException::new);
        if (!Tools.isNullOrEmpty(updatedProfile.getEmail()) && !existingProfile.getEmail().equals(updatedProfile.getEmail()))
            throw new DatabaseExceptions.BadRequestException("Email cannot be changed");

        // only update the fields that are not null and different from the existing profile
        if (!Tools.isNullOrEmpty(updatedProfile.getFirstName()))
            existingProfile.setFirstName(updatedProfile.getFirstName());
        if (!Tools.isNullOrEmpty(updatedProfile.getLastName()))
            existingProfile.setLastName(updatedProfile.getLastName());
        if (!Tools.isNullOrEmpty(updatedProfile.getPhoneNumber()))
            existingProfile.setPhoneNumber(updatedProfile.getPhoneNumber());
        if (!Tools.isNullOrEmpty(updatedProfile.getGender()))
            existingProfile.setGender(updatedProfile.getGender());
        if (!Tools.isNullOrEmpty(updatedProfile.getBirthDate()))
            existingProfile.setBirthDate(updatedProfile.getBirthDate());

        return profileRepository.save(existingProfile);
    }

    @Override
    public void deleteById(String id) {
        if (!profileRepository.findById(id).isPresent())
            throw new DatabaseExceptions.EntityNotFoundException();
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


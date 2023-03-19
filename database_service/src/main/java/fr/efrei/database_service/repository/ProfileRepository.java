package fr.efrei.database_service.repository;

// ProfileRepository.java

import fr.efrei.database_service.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
    List<Profile> findByLastName(String lastName);

    List<Profile> findByFirstName(String firstName);
}

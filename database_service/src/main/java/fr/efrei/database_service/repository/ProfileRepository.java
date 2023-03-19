package fr.efrei.database_service.repository;

// ProfileRepository.java

import fr.efrei.database_service.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
}

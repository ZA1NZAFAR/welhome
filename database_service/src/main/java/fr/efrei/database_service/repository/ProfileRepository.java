package fr.efrei.database_service.repository;

import fr.efrei.database_service.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {
    List<ProfileEntity> findByFirstName(String firstName);

    List<ProfileEntity> findByLastName(String lastName);

    List<ProfileEntity> findByPhoneNumber(String phoneNumber);

    List<ProfileEntity> findByBirthDate(Date birthDate);

    List<ProfileEntity> findByBirthDateBetween(Date birthDate1, Date birthDate2);

    List<ProfileEntity> findByGender(String gender);
}

package fr.efrei.database_service.repository;

import fr.efrei.database_service.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/*
Missing : state, owner_email
 */
@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {
    List<PropertyEntity> findByTitle(String title);
    List<PropertyEntity> findByDescription(String description);
    List<PropertyEntity> findByAddress(String description);

    List<PropertyEntity> findByCity(String City);

    List<PropertyEntity> findByCountry(String description);

    List<PropertyEntity> findByPrice(BigDecimal description);

    List<PropertyEntity> findByFloors(int floors);

    List<PropertyEntity> findByCapacity(int capacity);

    List<PropertyEntity> findByConstructionDate(Date construction_date);

    List<PropertyEntity> findByPublishDate(Date publish_date);

    List<PropertyEntity> findByArea(float area);

    List<PropertyEntity> findByEmail(String email);

    List<PropertyEntity> findByPropertyType(String property_type);

    List<PropertyEntity> findByZipCode(int zip_code);

}

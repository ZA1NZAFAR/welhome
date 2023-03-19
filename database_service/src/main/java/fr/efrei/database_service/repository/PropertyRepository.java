package fr.efrei.database_service.repository;

import fr.efrei.database_service.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}

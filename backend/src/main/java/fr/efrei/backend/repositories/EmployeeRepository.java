package fr.efrei.backend.repositories;

import fr.efrei.backend.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

// Pair<DomainType, Id>
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}

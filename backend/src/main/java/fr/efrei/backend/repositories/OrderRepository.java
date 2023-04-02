package fr.efrei.backend.repositories;

import fr.efrei.backend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<Order, Long> {

}

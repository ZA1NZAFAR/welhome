package fr.efrei.database_service.repository;

import fr.efrei.database_service.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByPropertyId (long propertyId );
    List<ReservationEntity> findByRenterEmail(String renterEmail);
    List<ReservationEntity> findByStartDate(Date startDate);
    List<ReservationEntity> findByEndDate(Date endDate);
    List<ReservationEntity> findByConfirmedOwner(boolean confirmedOwner);
    List<ReservationEntity> findByConfirmedRenter(boolean confirmedRenter);
}

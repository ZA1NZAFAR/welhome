package fr.efrei.database_service.service;

import fr.efrei.database_service.entity.ReservationEntity;
import fr.efrei.database_service.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ReservationService implements CRUD<ReservationEntity, Long> {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public ReservationEntity save(ReservationEntity reservation) {
        return this.reservationRepository.save(reservation);  }

    @Override
    public ReservationEntity findById(Long id) {
        return this.reservationRepository.findById(id).orElse(null);
    }

    @Override
    public ReservationEntity update(Long id, ReservationEntity reservation) {
        reservation.setId(id);
        return reservationRepository.save(reservation);
    }

    public List<ReservationEntity> findAll() {
        return reservationRepository.findAll();
    }

    public List<ReservationEntity> findByPropertyId (long propertyId) {
        return this.reservationRepository.findByPropertyId(propertyId);
    }
    public List<ReservationEntity> findByRenterEmail (String renterEmail) {
        return this.reservationRepository.findByRenterEmail(renterEmail);
    }

    public List<ReservationEntity> findByStartDate (Date startDate) {
        return this.reservationRepository.findByStartDate(startDate);
    }
    public List<ReservationEntity> findByEndDate (Date endDate) {
        return this.reservationRepository.findByEndDate(endDate);
    }
    public List<ReservationEntity> findByConfirmedOwner (boolean confirmedOwner) {
        return this.reservationRepository.findByConfirmedOwner(confirmedOwner);
    }

    public List<ReservationEntity> findByConfirmedRenter (boolean confirmedRenter) {
        return this.reservationRepository.findByConfirmedRenter(confirmedRenter);
    }

    public List<ReservationEntity> findByTotalPrice (float totalPrice) {
        return this.reservationRepository.findByTotalPrice(totalPrice);
    }

    @Override
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}

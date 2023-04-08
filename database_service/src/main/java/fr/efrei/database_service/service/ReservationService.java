package fr.efrei.database_service.service;

import fr.efrei.database_service.entity.ReservationEntity;
import fr.efrei.database_service.exception.DatabaseExceptions;
import fr.efrei.database_service.repository.ReservationRepository;
import fr.efrei.database_service.tools.Tools;
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
        if (reservationRepository.findById(reservation.getId()).isPresent())
            throw new DatabaseExceptions.EntityAlreadyExistsException();
        return this.reservationRepository.save(reservation);
    }

    @Override
    public ReservationEntity findById(Long id) {
        return this.reservationRepository.findById(id).orElseThrow(DatabaseExceptions.EntityNotFoundException::new);
    }

    @Override
    public ReservationEntity update(Long id, ReservationEntity updatedReservation) {
        ReservationEntity existingReservation = reservationRepository.findById(id).orElseThrow(DatabaseExceptions.EntityNotFoundException::new);
        if (!Tools.isNullOrEmpty(updatedReservation.getPropertyId()) && existingReservation.getId() != (updatedReservation.getId()))
            throw new DatabaseExceptions.BadRequestException("Id cannot be changed");

        // update only if the new value is different from the existing one
        if (!Tools.isNullOrEmpty(updatedReservation.getPropertyId()))
            existingReservation.setPropertyId(updatedReservation.getPropertyId());
        if (!Tools.isNullOrEmpty(updatedReservation.getRenterEmail()))
            existingReservation.setRenterEmail(updatedReservation.getRenterEmail());
        if (!Tools.isNullOrEmpty(updatedReservation.getStartDate()))
            existingReservation.setStartDate(updatedReservation.getStartDate());
        if (!Tools.isNullOrEmpty(updatedReservation.getEndDate()))
            existingReservation.setEndDate(updatedReservation.getEndDate());
        if (!Tools.isNullOrEmpty(updatedReservation.isConfirmedOwner()))
            existingReservation.setConfirmedOwner(updatedReservation.isConfirmedOwner());
        if (!Tools.isNullOrEmpty(updatedReservation.isConfirmedRenter()))
            existingReservation.setConfirmedRenter(updatedReservation.isConfirmedRenter());

        return reservationRepository.save(existingReservation);
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

    @Override
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}

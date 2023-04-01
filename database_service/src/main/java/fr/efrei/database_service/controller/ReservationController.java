package fr.efrei.database_service.controller;

import fr.efrei.database_service.entity.ReservationEntity;
import fr.efrei.database_service.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<ReservationEntity> getAllReservations() {
        return this.reservationService.findAll();
    }
    @PostMapping
    public ReservationEntity createReservation(@RequestBody ReservationEntity reservation) {
        return this.reservationService.save(reservation);
    }
    @GetMapping("/id/{reservationId}")
    public ReservationEntity getReservationId(@PathVariable long reservationId) {
        return this.reservationService.findById(reservationId);
    }
    @GetMapping("/propertyId/{propertyId}")
    public List<ReservationEntity> getPropertyId(@PathVariable long propertyId) {
        return this.reservationService.findByPropertyId(propertyId);
    }
    @GetMapping("/renterEmail/{renterEmail}")
    public List<ReservationEntity> getRenterEmail(@PathVariable String renterEmail) {
        return this.reservationService.findByRenterEmail(renterEmail);
    }
    @GetMapping("/startDate/{startDate}")
    public List<ReservationEntity> getStartDate(@PathVariable Date startDate) {
        return this.reservationService.findByStartDate(startDate);
    }
    @GetMapping("/endDate/{endDate}")
    public List<ReservationEntity> getEndDate(@PathVariable Date endDate) {
        return this.reservationService.findByEndDate(endDate);
    }
    @GetMapping("/confirmedOwner/{confirmedOwner}")
    public List<ReservationEntity> getConfirmedOwner(@PathVariable boolean confirmedOwner) {
        return this.reservationService.findByConfirmedOwner(confirmedOwner);
    }
    @GetMapping("/confirmedRenter/{confirmedRenter}")
    public List<ReservationEntity> getConfirmedRenter(@PathVariable boolean confirmedRenter) {
        return this.reservationService.findByConfirmedRenter(confirmedRenter);
    }
    @GetMapping("/totalPrice/{totalPrice}")
    public List<ReservationEntity> getTotalPricer(@PathVariable float totalPrice) {
        return this.reservationService.findByTotalPrice(totalPrice);
    }
    @PutMapping("/id/{reservationId}")
    public ReservationEntity updateReservation(@PathVariable long reservationId, @RequestBody ReservationEntity reservation) {
        return reservationService.update(reservationId, reservation);
    }
    @DeleteMapping("/id/{reservationId}")
    public void deleteReservation(@PathVariable long reservationId) {
        this.reservationService.deleteById(reservationId);
    }
}

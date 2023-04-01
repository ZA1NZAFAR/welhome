package fr.efrei.database_service.controller;

import fr.efrei.database_service.entity.ReservationEntity;
import fr.efrei.database_service.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{reservationId}")
    public ReservationEntity getReservationId(@PathVariable long reservationId) {
        return this.reservationService.findById(reservationId);
    }

    @PutMapping("/{reservationId}")
    public ReservationEntity updateReservation(@PathVariable long reservationId, @RequestBody ReservationEntity reservation) {
        return reservationService.update(reservationId, reservation);
    }
}

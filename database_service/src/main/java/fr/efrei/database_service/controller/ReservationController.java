package fr.efrei.database_service.controller;

import fr.efrei.database_service.dto.ReservationDTO;
import fr.efrei.database_service.entity.ReservationEntity;
import fr.efrei.database_service.service.ReservationService;
import fr.efrei.database_service.tools.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<ReservationDTO> getAllReservations() {
        return this.reservationService.findAll().stream().map(property -> Mapper.convertToDto(property, ReservationDTO.class)).collect(Collectors.toList());
    }
    @PostMapping
    public ReservationDTO createReservation(@RequestBody ReservationEntity reservation) {
        return Mapper.convertToDto(this.reservationService.save(reservation), ReservationDTO.class);

    }
    @GetMapping("/{reservationId}")
    public ReservationDTO getReservationId(@PathVariable long reservationId) {
        return Mapper.convertToDto(this.reservationService.findById(reservationId), ReservationDTO.class);

    }
    @GetMapping("/propertyId/{propertyId}")
    public List<ReservationDTO> getPropertyId(@PathVariable long propertyId) {
        return this.reservationService.findByPropertyId(propertyId).stream().map(property -> Mapper.convertToDto(property, ReservationDTO.class)).collect(Collectors.toList());
    }
    @GetMapping("/renterEmail/{renterEmail}")
    public List<ReservationDTO> getRenterEmail(@PathVariable String renterEmail) {
        return this.reservationService.findByRenterEmail(renterEmail).stream().map(property -> Mapper.convertToDto(property, ReservationDTO.class)).collect(Collectors.toList());

    }
    @GetMapping("/startDate/{startDate}")
    public List<ReservationDTO> getStartDate(@PathVariable Date startDate) {
        return this.reservationService.findByStartDate(startDate).stream().map(property -> Mapper.convertToDto(property, ReservationDTO.class)).collect(Collectors.toList());

    }
    @GetMapping("/endDate/{endDate}")
    public List<ReservationDTO> getEndDate(@PathVariable Date endDate) {
        return this.reservationService.findByEndDate(endDate).stream().map(property -> Mapper.convertToDto(property, ReservationDTO.class)).collect(Collectors.toList());

    }
    @GetMapping("/confirmedOwner/{confirmedOwner}")
    public List<ReservationDTO> getConfirmedOwner(@PathVariable boolean confirmedOwner) {
        return this.reservationService.findByConfirmedOwner(confirmedOwner).stream().map(property -> Mapper.convertToDto(property, ReservationDTO.class)).collect(Collectors.toList());

    }
    @GetMapping("/confirmedRenter/{confirmedRenter}")
    public List<ReservationDTO> getConfirmedRenter(@PathVariable boolean confirmedRenter) {
        return this.reservationService.findByConfirmedRenter(confirmedRenter).stream().map(property -> Mapper.convertToDto(property, ReservationDTO.class)).collect(Collectors.toList());

    }
    @PutMapping("/{reservationId}")
    public ReservationDTO updateReservation(@PathVariable long reservationId, @RequestBody ReservationEntity reservation) {
        return Mapper.convertToDto(reservationService.update(reservationId, reservation), ReservationDTO.class);

    }
    @DeleteMapping("/{reservationId}")
    public void deleteReservation(@PathVariable long reservationId) {
        this.reservationService.deleteById(reservationId);
    }
}

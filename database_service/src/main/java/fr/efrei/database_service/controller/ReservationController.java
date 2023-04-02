package fr.efrei.database_service.controller;

import fr.efrei.database_service.dto.ReservationDTO;
import fr.efrei.database_service.entity.ReservationEntity;
import fr.efrei.database_service.service.ReservationService;
import fr.efrei.database_service.tools.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Reservation", description = "This API will allow to carry out different operations on the reservations made by the renters.")

@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    @Operation(summary = "This endPoint will allow to retrieve all reservations made by renters")
    public List<ReservationDTO> getAllReservations() {
        return this.reservationService.findAll().stream().map(property -> Mapper.convertToDto(property, ReservationDTO.class)).collect(Collectors.toList());
    }
    @PostMapping
    @Operation(summary = "This endPoint will allow to create a new booking for a specific renter ")
    public ReservationDTO createReservation(@RequestBody ReservationEntity reservation) {
        return Mapper.convertToDto(this.reservationService.save(reservation), ReservationDTO.class);

    }
    @GetMapping("/{reservationId}")
    @Operation(summary = "This endPoint will allow to display one of the bookings by providing the relevant booking id ")
    public ReservationDTO getReservationId(@PathVariable long reservationId) {
        return Mapper.convertToDto(this.reservationService.findById(reservationId), ReservationDTO.class);

    }
    @GetMapping("/propertyId/{propertyId}")
    @Operation(summary = "This endPoint will allow to display all bookings with the relevant property id ")
    public List<ReservationDTO> getPropertyId(@PathVariable long propertyId) {
        return this.reservationService.findByPropertyId(propertyId).stream().map(property -> Mapper.convertToDto(property, ReservationDTO.class)).collect(Collectors.toList());
    }
    @GetMapping("/renterEmail/{renterEmail}")
    @Operation(summary = "This endPoint will allow to display all bookings made by a person, by providing an e-mail address ")
    public List<ReservationDTO> getRenterEmail(@PathVariable String renterEmail) {
        return this.reservationService.findByRenterEmail(renterEmail).stream().map(property -> Mapper.convertToDto(property, ReservationDTO.class)).collect(Collectors.toList());

    }
    @GetMapping("/startDate/{startDate}")
    @Operation(summary = "This endPoint will allow to display all bookings made on a specific start date ")
    public List<ReservationDTO> getStartDate(@PathVariable Date startDate) {
        return this.reservationService.findByStartDate(startDate).stream().map(property -> Mapper.convertToDto(property, ReservationDTO.class)).collect(Collectors.toList());

    }
    @GetMapping("/endDate/{endDate}")
    @Operation(summary = "This endPoint will allow to display all bookings made on a specific end date ")
    public List<ReservationDTO> getEndDate(@PathVariable Date endDate) {
        return this.reservationService.findByEndDate(endDate).stream().map(property -> Mapper.convertToDto(property, ReservationDTO.class)).collect(Collectors.toList());

    }
    @GetMapping("/confirmedOwner/{confirmedOwner}")
    @Operation(summary = "This endPoint will allow to know if the owner has confirmed the reservation")
    public List<ReservationDTO> getConfirmedOwner(@PathVariable boolean confirmedOwner) {
        return this.reservationService.findByConfirmedOwner(confirmedOwner).stream().map(property -> Mapper.convertToDto(property, ReservationDTO.class)).collect(Collectors.toList());

    }
    @GetMapping("/confirmedRenter/{confirmedRenter}")
    @Operation(summary = "This endPoint will allow to know if the renter has confirmed the reservation")
    public List<ReservationDTO> getConfirmedRenter(@PathVariable boolean confirmedRenter) {
        return this.reservationService.findByConfirmedRenter(confirmedRenter).stream().map(property -> Mapper.convertToDto(property, ReservationDTO.class)).collect(Collectors.toList());

    }
    @PutMapping("/{reservationId}")
    @Operation(summary = "This endPoint will allow to update a booking by providing the relevant booking id")
    public ReservationDTO updateReservation(@PathVariable long reservationId, @RequestBody ReservationEntity reservation) {
        return Mapper.convertToDto(reservationService.update(reservationId, reservation), ReservationDTO.class);

    }
    @DeleteMapping("/{reservationId}")
    @Operation(summary = "This endPoint will allow to delete a booking by providing the relevant booking id")
    public void deleteReservation(@PathVariable long reservationId) {
        this.reservationService.deleteById(reservationId);
    }
}

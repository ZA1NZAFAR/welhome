package fr.efrei.backend.controllers;

import fr.efrei.backend.entities.Reservation;
import fr.efrei.backend.utils.ResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "Reservations", description = "This API allows to carry out different operations on the reservations made by the renters")
@RequestMapping("/api/reservations")
public class ReservationsController {
    @Value("${databaseService.url}/reservations")
    private String URL;

    // Singleton instances of ResponseGenerator
    private ResponseGenerator<List<Reservation>> listGenerator;         // to send out JSON array in Response
    private ResponseGenerator<Reservation> generator;                   // to send out JSON object in Response

    @PostConstruct
    @Autowired
    private void init() {
        listGenerator = new ResponseGenerator<>();
        generator = new ResponseGenerator<>();
    }

    @GetMapping
    @Operation(summary = "This endpoint allows to retrieve all reservations made by renters")
    public ResponseEntity<List<Reservation>> getReservations() {
        ResponseEntity<List<Reservation>> result = listGenerator.buildRequest(URL, HttpMethod.GET, new ParameterizedTypeReference<List<Reservation>>() {});
        return result;
    }

    @GetMapping("/{id}")
    @Operation(summary = "This endpoint allows to display one of the bookings by providing the relevant reservation id")
    public ResponseEntity<Reservation> getReservation(@PathVariable String id) {
        ResponseEntity<Reservation> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.GET, new ParameterizedTypeReference<Reservation>() {});
        return result;
    }

    @PostMapping
    @Operation(summary = "This endpoint allows to create a new booking for a specific renter")
    @ApiResponse(responseCode = "201", description = "Creates a booking")
    public ResponseEntity<Reservation> postReservation(HttpServletRequest request, @RequestBody Reservation reservation) {
        String accessToken = request.getHeader("Authorization");
        if (TokenValidationUtil.isValidToken(accessToken)) {
            ResponseEntity<Reservation> result = generator.buildRequest(URL, HttpMethod.POST, reservation, new ParameterizedTypeReference<Reservation>() {});
            return result;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "This endpoint will allow to update a booking by providing the relevant booking id if exists, if not it creates one")
    @ApiResponse(responseCode = "200", description = "Updates a booking")
    @ApiResponse(responseCode = "201", description = "Creates a booking")
    public ResponseEntity<Reservation> putReservation(@PathVariable String id, @RequestBody Reservation reservation) {
        ResponseEntity<Reservation> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.PUT, reservation, new ParameterizedTypeReference<Reservation>() {});
        return result;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "This endpoint allows to delete a booking by providing the relevant booking id")
    @ApiResponse(responseCode = "200", description = "Deletes a booking")
    @ApiResponse(responseCode = "404", description = "If booking has not been found")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable String id) {
        ResponseEntity<Reservation> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.DELETE, new ParameterizedTypeReference<Reservation>() {});
        return result;
    }

    @GetMapping("/property_id/{propertyId}")
    @Operation(summary = "This endpoint allows to display all bookings with the relevant property id")
    public ResponseEntity<List<Reservation>> getReservationByPropertyId(@PathVariable String propertyId) {
        ResponseEntity<List<Reservation>> result = listGenerator.buildRequest(URL.concat("/propertyId/" + propertyId), HttpMethod.GET, new ParameterizedTypeReference<List<Reservation>>() {});
        return result;
    }

    @GetMapping("/renter_email/{renterEmail}")
    @Operation(summary = "This endpoint allows to display all bookings made by a person, by providing renter's e-mail address")
    public ResponseEntity<List<Reservation>> getReservationByRenterEmail(@PathVariable String renterEmail) {
        ResponseEntity<List<Reservation>> result = listGenerator.buildRequest(URL.concat("/renterEmail/" + renterEmail), HttpMethod.GET, new ParameterizedTypeReference<List<Reservation>>() {});
        return result;
    }

    @GetMapping("/start_date/{startDate}")
    @Operation(summary = "This endpoint allows to display all bookings made on a specific start date")
    public ResponseEntity<List<Reservation>> getReservationByStartDate(@PathVariable String startDate) {
        ResponseEntity<List<Reservation>> result = listGenerator.buildRequest(URL.concat("/startDate/" + startDate), HttpMethod.GET, new ParameterizedTypeReference<List<Reservation>>() {});
        return result;
    }

    @GetMapping("/end_date/{endDate}")
    @Operation(summary = "This endpoint allows to display all bookings made on a specific end date")
    public ResponseEntity<List<Reservation>> getReservationByEndDate(@PathVariable String endDate) {
        ResponseEntity<List<Reservation>> result = listGenerator.buildRequest(URL.concat("/endDate/" + endDate), HttpMethod.GET, new ParameterizedTypeReference<List<Reservation>>() {});
        return result;
    }

    @GetMapping("/confirmed_owner/{confirmedOwner}")
    @Operation(summary = "This endpoint allows to know if the owner has confirmed the reservation")
    public ResponseEntity<List<Reservation>> getReservationByConfirmedOwner(@PathVariable String confirmedOwner) {
        ResponseEntity<List<Reservation>> result = listGenerator.buildRequest(URL.concat("/confirmedOwner/" + confirmedOwner), HttpMethod.GET, new ParameterizedTypeReference<List<Reservation>>() {});
        return result;
    }

    @GetMapping("/confirmed_renter/{confirmedRenter}")
    @Operation(summary = "This endpoint allows to know if the renter has confirmed the reservation")
    public ResponseEntity<List<Reservation>> getReservationByConfirmedRenter(@PathVariable String confirmedRenter) {
        ResponseEntity<List<Reservation>> result = listGenerator.buildRequest(URL.concat("/confirmedRenter/" + confirmedRenter), HttpMethod.GET, new ParameterizedTypeReference<List<Reservation>>() {});
        return result;
    }
}

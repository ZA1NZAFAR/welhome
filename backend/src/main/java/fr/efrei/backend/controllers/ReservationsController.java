package fr.efrei.backend.controllers;

import fr.efrei.backend.entities.Property;
import fr.efrei.backend.entities.Reservation;
import fr.efrei.backend.entities.Review;
import fr.efrei.backend.utils.ResponseGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public ResponseEntity<List<Reservation>> getReservations() {
        ResponseEntity<List<Reservation>> result = listGenerator.buildRequest(URL, HttpMethod.GET, new ParameterizedTypeReference<List<Reservation>>() {});
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable String id) {
        ResponseEntity<Reservation> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.GET, new ParameterizedTypeReference<Reservation>() {});
        return result;
    }

    @PostMapping
    public ResponseEntity<Reservation> postReservation(@RequestBody Reservation reservation) {
        ResponseEntity<Reservation> result = generator.buildRequest(URL, HttpMethod.POST, reservation, new ParameterizedTypeReference<Reservation>() {});
        return result;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> putReservation(@PathVariable String id, @RequestBody Reservation reservation) {
        ResponseEntity<Reservation> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.PUT, reservation, new ParameterizedTypeReference<Reservation>() {});
        return result;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable String id) {
        ResponseEntity<Reservation> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.DELETE, new ParameterizedTypeReference<Reservation>() {});
        return result;
    }

    @GetMapping("/property_id/{propertyId}")
    public ResponseEntity<List<Reservation>> getPropertyByPropertyId(@PathVariable String propertyId) {
        ResponseEntity<List<Reservation>> result = listGenerator.buildRequest(URL.concat("/propertyId/" + propertyId), HttpMethod.GET, new ParameterizedTypeReference<List<Reservation>>() {});
        return result;
    }

    @GetMapping("/renter_email/{renterEmail}")
    public ResponseEntity<List<Reservation>> getPropertyByRenterEmail(@PathVariable String renterEmail) {
        ResponseEntity<List<Reservation>> result = listGenerator.buildRequest(URL.concat("/renterEmail/" + renterEmail), HttpMethod.GET, new ParameterizedTypeReference<List<Reservation>>() {});
        return result;
    }
}

package fr.efrei.backend.controllers;

import fr.efrei.backend.entities.*;
import fr.efrei.backend.utils.ResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Queries", description = "This API allows to perform various complex queries")
@RequestMapping("/api/queries")
public class QueriesController {
    @Value("${databaseService.url}")
    private String URL;

    // Helper method used to retrieve owner's profile
    private Profile retrieveOwner(String owner_email) {
        ResponseGenerator<Profile> ownerGenerator = new ResponseGenerator<>();
        ResponseEntity<Profile> ownerResult = ownerGenerator.buildRequest(URL + "/profiles/" + owner_email, HttpMethod.GET, new ParameterizedTypeReference<Profile>() {});
        return ownerResult.getBody();
    }

    @GetMapping("/owner_properties")
    @Operation(summary = "This endpoint allows to retrieve all properties the owner has in possession")
    @ApiResponse(responseCode = "200", description = "Owner and their properties have been found")
    @ApiResponse(responseCode = "404", description = "Owner doesn't exist and/or no properties have been found")
    public ResponseEntity<?> getOwnerProperties(@RequestParam(value="owner_email") String ownerEmail) {
        ResponseGenerator<Profile> ownerGenerator = new ResponseGenerator<>();
        ResponseGenerator<List<Property>> propertiesGenerator = new ResponseGenerator<>();

        // Retrieve owner
        final Profile owner = retrieveOwner(ownerEmail);
        // Retrieve properties
        ResponseEntity<List<Property>> propertiesResult = propertiesGenerator.buildRequest(URL + "/properties/owner_email/" + ownerEmail, HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        final List<Property> properties = propertiesResult.getBody();

        // Handle edge cases
        if (owner == null) {
            return new ResponseEntity<String>("Owner has not been found", HttpStatus.NOT_FOUND);
        } else if (properties == null) {
            return new ResponseEntity<String>("Properties have not been found", HttpStatus.NOT_FOUND);
        }

        // Create OwnerProperties DTO
        OwnerProperties ownerProperties = new OwnerProperties();
        ownerProperties.setOwner(owner);
        ownerProperties.setProperties(properties);

        ResponseEntity<OwnerProperties> result = new ResponseEntity<>(ownerProperties, HttpStatus.OK);
        return result;
    }

    @GetMapping("/owner_booked_properties")
    @Operation(summary = "This endpoint allows to retrieve all booked properties the owner has in possession")
    @ApiResponse(responseCode = "200", description = "Owner, their properties and bookings have been found")
    @ApiResponse(responseCode = "404", description = "Owner doesn't exist, no properties have been found and/or no bookings have been found")
    public ResponseEntity<?> getOwnerBookedProperties(@RequestParam(value="owner_email") String ownerEmail) {
        ResponseGenerator<Profile> userGenerator = new ResponseGenerator<>();
        ResponseGenerator<List<Property>> propertiesGenerator = new ResponseGenerator<>();
        ResponseGenerator<List<Reservation>> reservationsGenerator = new ResponseGenerator<>();

        // Retrieve owner
        final Profile owner = retrieveOwner(ownerEmail);
        // Retrieve properties
        ResponseEntity<List<Property>> propertiesResult = propertiesGenerator.buildRequest(URL + "/properties", HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        // Retrieve reservations
        ResponseEntity<List<Reservation>> reservationsResult = reservationsGenerator.buildRequest(URL + "/reservations", HttpMethod.GET, new ParameterizedTypeReference<List<Reservation>>() {});

        // Handle edge cases
        if (owner == null) {
            return new ResponseEntity<String>("Owner has not been found", HttpStatus.NOT_FOUND);
        } else if (propertiesResult.getBody() == null) {
            return new ResponseEntity<String>("Properties have not been found", HttpStatus.NOT_FOUND);
        } else if (reservationsResult.getBody() == null) {
            return new ResponseEntity<String>("Reservations have not been found", HttpStatus.NOT_FOUND);
        }

        // Filter out properties, to leave out only the ones belonging to owner
        final List<Property> properties = propertiesResult.getBody().stream()
                .filter(property ->
                        property.getOwnerEmail().equals(ownerEmail)
                ).collect(Collectors.toList());

        // Filter out reservations, to leave out only the ones for owner's properties
        final List<Reservation> reservations = reservationsResult.getBody().stream()
                .filter(reservation ->
                        propertiesResult.getBody().stream()
                                .anyMatch(property ->
                                        property.getOwnerEmail().equals(ownerEmail) && reservation.getPropertyId().compareTo(property.getId()) == 0
                                )
                ).collect(Collectors.toList());

        // Create OwnerBookedProperties DTO
        OwnerBookedProperties ownerBookedProperties = new OwnerBookedProperties();
        ownerBookedProperties.setOwner(owner);
        ownerBookedProperties.setProperties(properties);
        ownerBookedProperties.setBookings(reservations);

        ResponseEntity<OwnerBookedProperties> result = new ResponseEntity<>(ownerBookedProperties, HttpStatus.OK);
        return result;
    }
}

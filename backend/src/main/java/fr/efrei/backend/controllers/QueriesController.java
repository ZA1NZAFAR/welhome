package fr.efrei.backend.controllers;

import fr.efrei.backend.entities.OwnerProperties;
import fr.efrei.backend.entities.Profile;
import fr.efrei.backend.entities.Property;
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

@RestController
@Tag(name = "Queries", description = "This API allows to perform various complex queries")
@RequestMapping("/api/queries")
public class QueriesController {
    @Value("${databaseService.url}")
    private String URL;

    @GetMapping("/owner_properties")
    @Operation(summary = "This endpoint allows to retrieve all properties the owner has in possession")
    @ApiResponse(responseCode = "200", description = "Owner and their properties have been found")
    @ApiResponse(responseCode = "404", description = "Owner doesn't exist and/or no properties have been found")
    public ResponseEntity<?> getOwnerProperties(@RequestParam(value="owner_email") String owner_email) {
        ResponseGenerator<Profile> userGenerator = new ResponseGenerator<>();
        ResponseGenerator<List<Property>> propertiesGenerator = new ResponseGenerator<>();

        // Retrieve user
        ResponseEntity<Profile> userResult = userGenerator.buildRequest(URL + "/profiles/" + owner_email, HttpMethod.GET, new ParameterizedTypeReference<Profile>() {});
        Profile owner = userResult.getBody();
        // Retrieve properties
        ResponseEntity<List<Property>> propertiesResult = propertiesGenerator.buildRequest(URL + "/properties/owner_email/" + owner_email, HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        List<Property> properties = propertiesResult.getBody();

        // Handle edge cases
        if (owner == null) {
            return new ResponseEntity<String>("Owner has not been found", HttpStatus.NOT_FOUND);
        } else if (properties == null) {
            return new ResponseEntity<String>("Properties have not been found", HttpStatus.NOT_FOUND);
        }

        // Create OwnerProperties DTO
        OwnerProperties userProperties = new OwnerProperties();
        userProperties.setOwner(owner);
        userProperties.setProperties(properties);

        ResponseEntity<OwnerProperties> result = new ResponseEntity<>(userProperties, HttpStatus.OK);
        return result;
    }
}

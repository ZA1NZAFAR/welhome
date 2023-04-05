package fr.efrei.backend.controllers;

import fr.efrei.backend.entities.Profile;
import fr.efrei.backend.entities.Property;
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
@RequestMapping("/api/properties")
public class PropertiesController {
    @Value("${databaseService.url}/properties")
    private String URL;

    // Singleton instances of ResponseGenerator
    private ResponseGenerator<List<Property>> listGenerator;         // to send out JSON array in Response
    private ResponseGenerator<Property> generator;                   // to send out JSON object in Response

    @PostConstruct
    @Autowired
    private void init() {
        listGenerator = new ResponseGenerator<>();
        generator = new ResponseGenerator<>();
    }

    @GetMapping
    public ResponseEntity<List<Property>> getProperties() {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL, HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getProperty(@PathVariable String id) {
        ResponseEntity<Property> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.GET, new ParameterizedTypeReference<Property>() {});
        return result;
    }

    @PostMapping
    public ResponseEntity<Property> postProperty(@RequestBody Property property) {
        ResponseEntity<Property> result = generator.buildRequest(URL, HttpMethod.POST, property, new ParameterizedTypeReference<Property>() {});
        return result;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> putProperty(@PathVariable String id, @RequestBody Property property) {
        ResponseEntity<Property> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.PUT, property, new ParameterizedTypeReference<Property>() {});
        return result;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Property> deleteProperty(@PathVariable String id) {
        ResponseEntity<Property> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.DELETE, new ParameterizedTypeReference<Property>() {});
        return result;
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Property>> getPropertyByTitle(@PathVariable String title) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/title/" + title), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/description/{description}")
    public ResponseEntity<List<Property>> getPropertyByDescription(@PathVariable String description) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/description/" + description), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    // DYSFUNCTIONAL: Currently does nothing, implemented for future use-case
    @GetMapping("/property_category/{propertyCategory}")
    public ResponseEntity<List<Property>> getPropertyByCategory(@PathVariable String propertyCategory) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/property_category/" + propertyCategory), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<List<Property>> getPropertyByAddress(@PathVariable String address) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/address/" + address), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Property>> getPropertyByCity(@PathVariable String city) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/city/" + city), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/zip_code/{zipCode}")
    public ResponseEntity<List<Property>> getPropertyByZipCode(@PathVariable String zipCode) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/zip_code/" + zipCode), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    // DYSFUNCTIONAL: Currently does nothing, implemented for future use-case
    @GetMapping("/state/{state}")
    public ResponseEntity<List<Property>> getPropertyByState(@PathVariable String state) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/state/" + state), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<Property>> getPropertyByCountry(@PathVariable String country) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/country/" + country), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<List<Property>> getPropertyByPrice(@PathVariable String price) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/price/" + price), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    // DYSFUNCTIONAL: Currently does nothing, implemented for future use-case
    @GetMapping("/surface_area/{surfaceArea}")
    public ResponseEntity<List<Property>> getPropertyBySurfaceArea(@PathVariable String surfaceArea) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/surface_area/" + surfaceArea), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/floors/{floors}")
    public ResponseEntity<List<Property>> getPropertyByNbFloors(@PathVariable String floors) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/floors/" + floors), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/capacity/{capacity}")
    public ResponseEntity<List<Property>> getPropertyByCapacity(@PathVariable String capacity) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/capacity/" + capacity), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/construction_date/{constructionDate}")
    public ResponseEntity<List<Property>> getPropertyByConstructionDate(@PathVariable String constructionDate) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/construction_date/" + constructionDate), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/publish_date/{publishDate}")
    public ResponseEntity<List<Property>> getPropertyByPublishDate(@PathVariable String publishDate) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/publish_date/" + publishDate), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/owner_email/{email}")
    public ResponseEntity<List<Property>> getPropertyByOwnerEmail(@PathVariable String email) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/owner_email/" + email), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }
}

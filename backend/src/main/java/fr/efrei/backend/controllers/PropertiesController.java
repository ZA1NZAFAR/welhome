package fr.efrei.backend.controllers;

import fr.efrei.backend.entities.Property;
import fr.efrei.backend.exceptions.CheckingQueryParametersFailedException;
import fr.efrei.backend.utils.ResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "Properties", description = "This API allows to perform a set of operations related to properties")
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
    @Operation(summary = "This endpoint allows to retrieve all properties")
    public ResponseEntity<List<Property>> getProperties() {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL, HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/{id}")
    @Operation(summary = "This endpoint allows to retrieve a property based on id")
    public ResponseEntity<Property> getProperty(@PathVariable String id) {
        ResponseEntity<Property> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.GET, new ParameterizedTypeReference<Property>() {});
        return result;
    }

    @PostMapping
    @Operation(summary = "This endpoint allows to create a new property")
    @ApiResponse(responseCode = "201", description = "Creates a property")
    public ResponseEntity<Property> postProperty(@RequestBody Property property) {
        ResponseEntity<Property> result = generator.buildRequest(URL, HttpMethod.POST, property, new ParameterizedTypeReference<Property>() {});
        return result;
    }

    @PutMapping("/{id}")
    @Operation(summary = "This endpoint allows to update a property based on id if property exists, otherwise it creates a new property")
    @ApiResponse(responseCode = "200", description = "Updates a property")
    @ApiResponse(responseCode = "201", description = "Creates a property")
    public ResponseEntity<Property> putProperty(@PathVariable String id, @RequestBody Property property) {
        ResponseEntity<Property> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.PUT, property, new ParameterizedTypeReference<Property>() {});
        return result;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "This endpoint allows to delete a property based on id")
    @ApiResponse(responseCode = "200", description = "Deletes a property")
    @ApiResponse(responseCode = "404", description = "Property has not been found")
    public ResponseEntity<Property> deleteProperty(@PathVariable String id) {
        ResponseEntity<Property> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.DELETE, new ParameterizedTypeReference<Property>() {});
        return result;
    }

    @GetMapping("/title/{title}")
    @Operation(summary = "This endpoint allows to retrieve a property based on title")
    public ResponseEntity<List<Property>> getPropertyByTitle(@PathVariable String title) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/title/" + title), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/description/{description}")
    @Operation(summary = "This endpoint allows to retrieve a property based on description")
    public ResponseEntity<List<Property>> getPropertyByDescription(@PathVariable String description) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/description/" + description), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/property_category/{propertyCategory}")
    @Operation(summary = "This endpoint allows to retrieve a property based on category")
    public ResponseEntity<List<Property>> getPropertyByCategory(@PathVariable String propertyCategory) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/property_category/" + propertyCategory), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/address/{address}")
    @Operation(summary = "This endpoint allows to retrieve a property based on address")
    public ResponseEntity<List<Property>> getPropertyByAddress(@PathVariable String address) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/address/" + address), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/city/{city}")
    @Operation(summary = "This endpoint allows to retrieve a property based on city name")
    public ResponseEntity<List<Property>> getPropertyByCity(@PathVariable String city) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/city/" + city), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/zip_code/{zipCode}")
    @Operation(summary = "This endpoint allows to retrieve a property based on zip code")
    public ResponseEntity<List<Property>> getPropertyByZipCode(@PathVariable String zipCode) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/zip_code/" + zipCode), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/state/{state}")
    @Operation(summary = "This endpoint allows to retrieve a property based on state name")
    public ResponseEntity<List<Property>> getPropertyByState(@PathVariable String state) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/state/" + state), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/country/{country}")
    @Operation(summary = "This endpoint allows to retrieve a property based on country name")
    public ResponseEntity<List<Property>> getPropertyByCountry(@PathVariable String country) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/country/" + country), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/price/{price}")
    @Operation(summary = "This endpoint allows to retrieve a property based on price")
    public ResponseEntity<List<Property>> getPropertyByPrice(@PathVariable String price) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/price/" + price), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/price_between")
    @Operation(summary = "This endpoint allows to retrieve a property based on price between 2 given price values", description = "Inclusive lower & upper bounds - search between 100$ and 1000$ yields properties which are valued both 100$ and 1000$")
    public ResponseEntity<List<Property>> getPropertyByPriceBetween(@RequestParam("min") String lowerBound, @RequestParam("max") String upperBound) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/price/" + lowerBound + "/" + upperBound), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/surface_area/{surfaceArea}")
    @Operation(summary = "This endpoint allows to retrieve a property based on surface area")
    public ResponseEntity<List<Property>> getPropertyBySurfaceArea(@PathVariable String surfaceArea) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/surface_area/" + surfaceArea), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/floors/{floors}")
    @Operation(summary = "This endpoint allows to retrieve a property based on number of floors")
    public ResponseEntity<List<Property>> getPropertyByNbFloors(@PathVariable String floors) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/floors/" + floors), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/capacity/{capacity}")
    @Operation(summary = "This endpoint allows to retrieve a property based on capacity")
    public ResponseEntity<List<Property>> getPropertyByCapacity(@PathVariable String capacity) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/capacity/" + capacity), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/construction_date/{constructionDate}")
    @Operation(summary = "This endpoint allows to retrieve a property based on construction date")
    public ResponseEntity<List<Property>> getPropertyByConstructionDate(@PathVariable String constructionDate) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/construction_date/" + constructionDate), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/publish_date/{publishDate}")
    @Operation(summary = "This endpoint allows to retrieve a property based on publish date")
    public ResponseEntity<List<Property>> getPropertyByPublishDate(@PathVariable String publishDate) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/publish_date/" + publishDate), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    @GetMapping("/owner_email/{email}")
    @Operation(summary = "This endpoint allows to retrieve a property based on owner email")
    public ResponseEntity<List<Property>> getPropertyByOwnerEmail(@PathVariable String email) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/owner_email/" + email), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

    // Enhanced queries, filtering out based on several attributes
    @GetMapping("/categories")
    @Operation(summary = "This endpoint allows to filter out properties based on several category attributes", description = "For example properties, associated with the following categories: House, Apartment, Room or House & Room, House & Apartment, Apartment & Room, House & Apartment & Room")
    @ApiResponse(responseCode = "200", description = "Properties have been found")
    @ApiResponse(responseCode = "404", description = "No properties have been found")
    public ResponseEntity<List<Property>> getPropertyBySeveralCategories(@RequestParam(value="category") List<String> categories) {
        List<Property> properties = new ArrayList<>();

        categories.forEach(category -> {
            ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/property_category/" + category), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
            properties.addAll(result.getBody());
        });

        ResponseEntity<List<Property>> result = new ResponseEntity<>(properties, HttpStatus.OK);
        return result;
    }

    @GetMapping("/cities")
    @Operation(summary = "This endpoint allows to filter out properties based on several city attributes", description = "Use case: for example, the retrieval of properties located in different cities - Los Angeles, Seattle, Houston, Chicago")
    @ApiResponse(responseCode = "200", description = "Properties have been found")
    @ApiResponse(responseCode = "404", description = "No properties have been found")
    public ResponseEntity<List<Property>> getPropertyBySeveralCities(@RequestParam(value="city") List<String> cities) {
        List<Property> properties = new ArrayList<>();

        cities.forEach(city -> {
            ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/city/" + city), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
            // Make union of properties based on several city type attributes
            properties.addAll(result.getBody());
        });

        ResponseEntity<List<Property>> result = new ResponseEntity<>(properties, HttpStatus.OK);
        return result;
    }

    @GetMapping("/owners")
    @Operation(summary = "This endpoint allows to filter out properties based on several owner attributes", description = "Use case: finding out properties of renters favorite owners (in case if renters want to re-rent from owners they trust the most)")
    @ApiResponse(responseCode = "200", description = "Properties have been found")
    @ApiResponse(responseCode = "404", description = "No properties have been found")
    public ResponseEntity<List<Property>> getPropertyBySeveralOwnerEmails(@RequestParam(value="owner") List<String> owners) {
        List<Property> properties = new ArrayList<>();

        owners.forEach(owner -> {
            ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/owner_email/" + owner), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
            // Make union of properties based on several city type attributes
            properties.addAll(result.getBody());
        });

        ResponseEntity<List<Property>> result = new ResponseEntity<>(properties, HttpStatus.OK);
        return result;
    }

    @GetMapping("/with_images")
    @Operation(summary = "This endpoint allows to find out properties containing specified number of images")
    @ApiResponse(responseCode = "200", description = "Properties have been found")
    @ApiResponse(responseCode = "403", description = "Query has not been properly formatted")
    @ApiResponse(responseCode = "404", description = "No properties have been found")
    public ResponseEntity<?> getPropertyByImagePresence(@RequestParam(value="quantity") int quantity) {
        ResponseEntity<List<Property>> allProperties = listGenerator.buildRequest(URL, HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        List<Property> properties;

        try {
            // Check image presence
            properties = allProperties.getBody().stream().filter(property -> {
                if (quantity == 0)
                    return (property.getImageUrl1() == null && property.getImageUrl2() == null && property.getImageUrl3() == null);
                else if (quantity == 1)
                    return (property.getImageUrl1() != null || property.getImageUrl2() != null || property.getImageUrl3() != null);
                else if (quantity == 2)
                    return ((property.getImageUrl1() != null && property.getImageUrl2() != null)
                            || (property.getImageUrl1() != null && property.getImageUrl3() != null)
                            || (property.getImageUrl2() != null && property.getImageUrl3() != null));
                else if (quantity == 3)
                    return (property.getImageUrl1() != null && property.getImageUrl2() != null && property.getImageUrl3() != null);
                else
                    throw new IllegalArgumentException("ERROR:\nProperties cannot contain asked number of images. Asked: " + quantity
                            + "\nMinimum allowed number of images is " + 0
                            + "\nMaximum allowed number of images is " + 3);
            }).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        ResponseEntity<List<Property>> result = new ResponseEntity<>(properties, HttpStatus.OK);
        return result;
    }

    @ExceptionHandler(CheckingQueryParametersFailedException.class)
    @GetMapping("/property")
    @Operation(summary = "This endpoint allows to find out properties containing specified/number of specified attributes", description = "Query parameter values are case-insensitive: Los Angeles == log angeles == LOS ANGELES")
    @ApiResponse(responseCode = "200", description = "Properties have been found")
    @ApiResponse(responseCode = "400", description = "Query has not been properly formatted")
    @ApiResponse(responseCode = "404", description = "No properties have been found")
    public ResponseEntity<?> getPropertyBySeveralAttributes(@RequestParam(value="category") Optional<List<String>> categories, @RequestParam(value="country") Optional<List<String>> countries,
                                                                         @RequestParam(value="state") Optional<List<String>> states, @RequestParam(value="city") Optional<List<String>> cities,
                                                                         @RequestParam(value="min_price") Optional<BigDecimal> minPrice, @RequestParam(value="max_price") Optional<BigDecimal> maxPrice,
                                                                         @RequestParam(value="min_surface_area") Optional<Float> minSurfaceArea, @RequestParam(value="max_surface_area") Optional<Float> maxSurfaceArea,
                                                                         @RequestParam(value="min_floors") Optional<Long> minFloors, @RequestParam(value="max_floors") Optional<Long> maxFloors,
                                                                         @RequestParam(value="min_capacity") Optional<Long> minCapacity, @RequestParam(value="max_capacity") Optional<Long> maxCapacity,
                                                                         @RequestParam(value="min_construction_date") Optional<Date> minConstructionDate, @RequestParam(value="max_construction_date") Optional<Date> maxConstructionDate) {
        ResponseEntity<List<Property>> allProperties = listGenerator.buildRequest(URL, HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});

        try {
            StringBuilder message = new StringBuilder();
            message.append("ERROR:");

            List<String> categoryTypes = List.of("house", "apartment", "room");
            if (categories.isPresent() && !categories.get().stream().allMatch(category -> categoryTypes.contains(category.toLowerCase())))
                message.append("\nCategory can only be one of 3 three types: House, Apartment, Room");

            if ((countries.isPresent() && states.isPresent() && (countries.get().stream().anyMatch(country -> states.get().stream().map(state -> state.toLowerCase()).collect(Collectors.toList()).contains(country.toLowerCase())))
            || (countries.isPresent() && cities.isPresent() && (countries.get().stream().anyMatch(country -> cities.get().stream().map(city -> city.toLowerCase()).collect(Collectors.toList()).contains(country.toLowerCase())))
            || states.isPresent() && cities.isPresent() && (states.get().stream().anyMatch(state -> cities.get().stream().map(city -> city.toLowerCase()).collect(Collectors.toList()).contains(state.toLowerCase()))))))
                message.append("\nCountry, state & city must all be distinct values");

            if (minPrice.isPresent() && maxPrice.isPresent() && (maxPrice.get().compareTo(minPrice.get()) <= 0))
                message.append("\nMaximum price can't be less than minimum price");

            if (minSurfaceArea.isPresent() && maxSurfaceArea.isPresent() && (maxSurfaceArea.get().compareTo(minSurfaceArea.get()) <= 0))
                message.append("\nMaximum surface area can't be less than minimum surface area");

            if (minFloors.isPresent() && maxFloors.isPresent() && (maxFloors.get().compareTo(minFloors.get()) <= 0))
                message.append("\nMaximum number of floors can't be less than minimum number of floors");

            if (minCapacity.isPresent() && maxCapacity.isPresent() && (maxCapacity.get().compareTo(minCapacity.get()) <= 0))
                message.append("\nMaximum capacity can't be less than minimum capacity");

            if (minConstructionDate.isPresent() && maxConstructionDate.isPresent() && (maxConstructionDate.get().compareTo(minConstructionDate.get()) <= 0))
                message.append("\nMaximum construction date can't be less than minimum construction date. Impossible to impose maximum construction date on a property which hasn't been built yet");

            if (message.toString().equals("ERROR:"))
                message.setLength(0);
            else
                throw new CheckingQueryParametersFailedException(message.toString());
        } catch (CheckingQueryParametersFailedException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

        List<Property> properties = allProperties.getBody().stream().filter(property ->
                ((categories.isPresent() ? categories.get().stream().anyMatch(category -> category.toLowerCase().equals(property.getPropertyCategory().toLowerCase())) : true)
                && (countries.isPresent() ? countries.get().stream().anyMatch(country -> country.toLowerCase().equals(property.getCountry().toLowerCase())) : true)
                && (states.isPresent() ?  states.get().stream().anyMatch(state -> state.toLowerCase().equals(property.getState().toLowerCase())) : true)
                && (cities.isPresent() ? cities.get().stream().anyMatch(city -> city.toLowerCase().equals(property.getCity().toLowerCase())) : true)
                && (minPrice.isPresent() ? property.getPrice().compareTo(minPrice.get()) >= 0 : true) && (maxPrice.isPresent() ? property.getPrice().compareTo(maxPrice.get()) <= 0 : true)
                && (minSurfaceArea.isPresent() ? property.getSurfaceArea().compareTo(minSurfaceArea.get()) >= 0 : true) && (maxSurfaceArea.isPresent() ? property.getSurfaceArea().compareTo(maxSurfaceArea.get()) <= 0 : true)
                && (minFloors.isPresent() ? property.getFloors().compareTo(minFloors.get()) >= 0 : true) && (maxFloors.isPresent() ? property.getFloors().compareTo(maxFloors.get()) <= 0 : true)
                && (minCapacity.isPresent() ? property.getCapacity().compareTo(minCapacity.get()) >= 0 : true) && (maxCapacity.isPresent() ? property.getCapacity().compareTo(maxCapacity.get()) <= 0 : true)
                && (minConstructionDate.isPresent() ? property.getConstructionDate().compareTo(minConstructionDate.get()) >= 0 : true) && (maxConstructionDate.isPresent() ? property.getConstructionDate().compareTo(maxConstructionDate.get()) <= 0 : true))
        ).collect(Collectors.toList());

        ResponseEntity<List<Property>> result = new ResponseEntity<>(properties, HttpStatus.OK);
        return result;
    }
}

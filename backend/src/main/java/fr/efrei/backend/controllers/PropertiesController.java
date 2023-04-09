package fr.efrei.backend.controllers;

import fr.efrei.backend.entities.Property;
import fr.efrei.backend.exceptions.CheckingQueryParametersFailedException;
import fr.efrei.backend.utils.ResponseGenerator;
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

    // Inclusive lower & upper bounds (search between 100$ and 1000$ yields properties which are valued both 100$ and 1000$)
    @GetMapping("/price_between")
    public ResponseEntity<List<Property>> getPropertyByPriceBetween(@RequestParam("min") String lowerBound, @RequestParam("max") String upperBound) {
        ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/price/" + lowerBound + "/" + upperBound), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
        return result;
    }

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

    // Enhanced queries, filtering out based on several attributes
    // Filters out properties based on several category attributes: (House & Room, House & Apartment, Apartment & Room)
    @GetMapping("/categories")
    public ResponseEntity<List<Property>> getPropertyBySeveralCategories(@RequestParam(value="category") List<String> categories) {
        List<Property> properties = new ArrayList<>();

        categories.forEach(category -> {
            ResponseEntity<List<Property>> result = listGenerator.buildRequest(URL.concat("/property_category/" + category), HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});
            properties.addAll(result.getBody());
        });

        ResponseEntity<List<Property>> result = new ResponseEntity<>(properties, HttpStatus.OK);
        return result;
    }

    // Filters out properties based on several city attributes. Use case: for example, retrieval of properties located in different cities (Los Angeles, Seattle, Houston, Chicago)
    @GetMapping("/cities")
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

    // Filters out properties based on several owner attributes. Use case: finding out properties of renters favorite owners (in case if renters want to re-rent from owners they trust the most)
    @GetMapping("/owners")
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

    // Provides properties containing specified number of images
    @ExceptionHandler
    @GetMapping("/with_images")
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

    // Find outs a property containing specified attributes
    @ExceptionHandler(CheckingQueryParametersFailedException.class)
    @GetMapping("/property")
    public ResponseEntity<?> getPropertyBySeveralAttributes(@RequestParam(value="category") Optional<String> category, @RequestParam(value="country") Optional<String> country,
                                                                         @RequestParam(value="state") Optional<String> state, @RequestParam(value="city") Optional<String> city,
                                                                         @RequestParam(value="min_price") Optional<BigDecimal> minPrice, @RequestParam(value="max_price") Optional<BigDecimal> maxPrice,
                                                                         @RequestParam(value="min_surface_area") Optional<Float> minSurfaceArea, @RequestParam(value="max_surface_area") Optional<Float> maxSurfaceArea,
                                                                         @RequestParam(value="min_floors") Optional<Long> minFloors, @RequestParam(value="max_floors") Optional<Long> maxFloors,
                                                                         @RequestParam(value="min_capacity") Optional<Long> minCapacity, @RequestParam(value="max_capacity") Optional<Long> maxCapacity,
                                                                         @RequestParam(value="min_construction_date") Optional<Date> minConstructionDate, @RequestParam(value="max_construction_date") Optional<Date> maxConstructionDate) {
        ResponseEntity<List<Property>> allProperties = listGenerator.buildRequest(URL, HttpMethod.GET, new ParameterizedTypeReference<List<Property>>() {});

        try {
            StringBuilder message = new StringBuilder();
            message.append("ERROR:");

            if (category.isPresent() && (!category.get().toLowerCase().equals("house") && !category.get().toLowerCase().equals("apartment") && !category.get().toLowerCase().equals("room")))
                message.append("\nCategory can only be one of 3 three types: House, Apartment, Room");

            if ((country.isPresent() && state.isPresent() && (country.get().toLowerCase().equals(state.get().toLowerCase())))
            || (country.isPresent() && city.isPresent() && (country.get().toLowerCase().equals(city.get().toLowerCase())))
            || state.isPresent() && city.isPresent() && (state.get().toLowerCase().equals(city.get().toLowerCase())))
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

        List<Property> properties = allProperties.getBody().stream().filter(property -> (
                (category.isPresent() ? property.getPropertyCategory().toLowerCase().equals(category.get().toLowerCase()) : true) && (country.isPresent() ? property.getCountry().toLowerCase().equals(country.get().toLowerCase()) : true)
                && (state.isPresent() ? property.getState().toLowerCase().equals(state.get().toLowerCase()) : true)  && (city.isPresent() ? property.getCity().toLowerCase().equals(city.get().toLowerCase()) : true)
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
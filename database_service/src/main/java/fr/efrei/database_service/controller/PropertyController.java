package fr.efrei.database_service.controller;

import fr.efrei.database_service.dto.PropertyDTO;
import fr.efrei.database_service.entity.PropertyEntity;
import fr.efrei.database_service.service.PropertyService;
import fr.efrei.database_service.tools.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@Tag(name = "Properties", description = "This API will allow us to perform a set of tasks related to properties")
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    @Operation(summary = "This endpoint will create a new property")
    public ResponseEntity<PropertyDTO> createProperty(@RequestBody PropertyEntity property) {
        PropertyEntity propertyToCreate = propertyService.save(property);
        if (propertyToCreate == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(Mapper.convert(propertyToCreate, PropertyDTO.class));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "This endpoint will allow to retrieve a property based on id")
    public ResponseEntity<PropertyDTO> getProperty(@PathVariable Long id){
        PropertyEntity propertyToGet = propertyService.findById(id);
        if(propertyToGet == null){
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(Mapper.convert(propertyToGet, PropertyDTO.class));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "This endpoint will allow to update a property based on id")
    public ResponseEntity<PropertyDTO> updateProperty(@PathVariable Long id, @RequestBody PropertyEntity property){
        PropertyEntity propertyToUpdate = propertyService.update(id, property);
        if(propertyToUpdate == null){
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(Mapper.convert(propertyToUpdate, PropertyDTO.class));
        }
    }

    @DeleteMapping({"/{id}"})
    @Operation(summary = "This endpoint will allow to delete a property based on id")
    public ResponseEntity<PropertyDTO> deleteProperty(@PathVariable Long id){
        try{
            this.propertyService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "This endpoint will allow to retrieve all properties")
    public List<PropertyDTO> getAllProperty(){
        return this.propertyService.findAll().stream().map(property -> Mapper.convert(property, PropertyDTO.class)).collect(Collectors.toList());
    }

    @GetMapping({"/title/{title}"})
    @Operation(summary = "This endpoint will allow to retrieve a property based on title")
    public List<PropertyDTO> getPropertyByTitle(@PathVariable String title){
        return this.propertyService.findByTitle(title).stream().map(property -> Mapper.convert(property, PropertyDTO.class)).collect(Collectors.toList());
    }

    @GetMapping({"/description/{description}"})
    @Operation(summary = "This endpoint will allow to retrieve a property based on description")
    public List<PropertyDTO> getPropertyByDescription(@PathVariable String description){
        return this.propertyService.findByDescription(description).stream().map(property -> Mapper.convert(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/property_category/{category}"})
    @Operation(summary = "This endpoint will allow to retrieve a property based on description")
    public List<PropertyDTO> getPropertyByPropertyCategory(@PathVariable String category){
        return this.propertyService.findByPropertyCategory(category).stream().map(property -> Mapper.convert(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/address/{address}"})
    @Operation(summary = "This endpoint will allow to retrieve a property based on address")
    public List<PropertyDTO> getPropertyByAddress(@PathVariable String address){
        return this.propertyService.findByAddress(address).stream().map(property -> Mapper.convert(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/city/{city}"})
    @Operation(summary = "This endpoint will allow to retrieve a property based on city name")
    public List<PropertyDTO> getPropertyByCity(@PathVariable String city){
        return this.propertyService.findByCity(city).stream().map(property -> Mapper.convert(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/state/{state}"})
    @Operation(summary = "This endpoint will allow to retrieve a property based on city name")
    public List<PropertyDTO> getPropertyByState(@PathVariable String state){
        return this.propertyService.findByState(state).stream().map(property -> Mapper.convert(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/country/{country}"})
    @Operation(summary = "This endpoint will allow to retrieve a property based on country name")
    public List<PropertyDTO> getPropertyByCountry(@PathVariable String country){
        return this.propertyService.findByCountry(country).stream().map(property -> Mapper.convert(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/price/{price}"})
    @Operation(summary = "This endpoint will allow to retrieve a property based on price")
    public List<PropertyDTO> getPropertyByPrice(@PathVariable BigDecimal price){
        return this.propertyService.findByPrice(price).stream().map(property -> Mapper.convert(property, PropertyDTO.class)).collect(Collectors.toList());
    }

    @GetMapping({"/surface_area/{area}"})
    @Operation(summary = "This endpoint will allow to retrieve a property based on price")
    public List<PropertyDTO> getPropertyByPrice(@PathVariable float area){
        return this.propertyService.findBySurfaceArea(area).stream().map(property -> Mapper.convert(property, PropertyDTO.class)).collect(Collectors.toList());
    }
    @GetMapping({"/owner_email/{email}"})
    @Operation(summary = "This endpoint will allow to retrieve a property based on owner email")
    public List<PropertyDTO> getPropertyByOwnerEmail(@PathVariable String email){
        return this.propertyService.findByOwnerEmail(email).stream().map(property -> Mapper.convert(property, PropertyDTO.class)).collect(Collectors.toList());
    }
    @GetMapping({"/floors/{floors}"})
    @Operation(summary = "This endpoint will allow to retrieve a property based on number of floors")
    public List<PropertyDTO> getPropertyByFloors(@PathVariable int floors){
        return this.propertyService.findByFloors(floors).stream().map(property -> Mapper.convert(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/capacity/{capacity}"})
    @Operation(summary = "This endpoint will allow to retrieve a property based on capacity")
    public List<PropertyDTO> getPropertyByCapacity(@PathVariable int capacity){
        return this.propertyService.findByCapacity(capacity).stream().map(property -> Mapper.convert(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/construction_date/{construction_date}"})
    @Operation(summary = "This endpoint will allow to retrieve a property based on construction date")
    public List<PropertyDTO> getPropertyByConstructionDate(@PathVariable Date construction_date){
        return this.propertyService.findByConstructionDate(construction_date).stream().map(property -> Mapper.convert(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/publish_date/{publish_date}"})
    @Operation(summary = "This endpoint will allow to retrieve a property based on publish date")
    public List<PropertyDTO> getPropertyByPublishDate(@PathVariable Date publish_date){
        return this.propertyService.findByPublishDate(publish_date).stream().map(property -> Mapper.convert(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/zip_code/{code}"})
    @Operation(summary = "This endpoint will allow to retrieve a property based on zip code")
    public List<PropertyDTO> getPropertyByZipCode(@PathVariable int code){
        return this.propertyService.findByZipCode(code).stream().map(property -> Mapper.convert(property, PropertyDTO.class)).collect(Collectors.toList());
    }

}
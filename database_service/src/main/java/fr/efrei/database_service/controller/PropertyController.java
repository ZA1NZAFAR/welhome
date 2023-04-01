package fr.efrei.database_service.controller;

import fr.efrei.database_service.entity.PropertyEntity;
import fr.efrei.database_service.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public PropertyEntity createProperty(@RequestBody PropertyEntity property){
        return this.propertyService.save(property);
    }

    @GetMapping("/{id}")
    public PropertyEntity getProperty(@PathVariable Long id){
        return this.propertyService.findById(id);
    }

    @PutMapping("/{id}")
    public PropertyEntity updateProperty(@PathVariable Long id, @RequestBody PropertyEntity property){
        return propertyService.update(id, property);
    }

    @DeleteMapping({"/{id}"})
    public void deleteProperty(@PathVariable Long id){
        this.propertyService.deleteById(id);
    }

    @GetMapping
    public List<PropertyEntity> getAllProperty(){
        return this.propertyService.findAll();
    }

    @GetMapping({"/title/{title}"})
    public List<PropertyEntity> getPropertyByTitle(@PathVariable String title){
        return this.propertyService.findByTitle(title);
    }

    @GetMapping({"/description/{description}"})
    public List<PropertyEntity> getPropertyByDescription(@PathVariable String description){
        return this.propertyService.findByDescription(description);
    }

    @GetMapping({"/address/{address}"})
    public List<PropertyEntity> getPropertyByAddress(@PathVariable String address){
        return this.propertyService.findByAddress(address);
    }

    @GetMapping({"/city/{city}"})
    public List<PropertyEntity> getPropertyByCity(@PathVariable String city){
        return this.propertyService.findByCity(city);
    }

    @GetMapping({"/country/{country}"})
    public List<PropertyEntity> getPropertyByCountry(@PathVariable String country){
        return this.propertyService.findByCountry(country);
    }

    @GetMapping({"/price/{price}"})
    public List<PropertyEntity> getPropertyByPrice(@PathVariable BigDecimal price){
        return this.propertyService.findByPrice(price);
    }

    @GetMapping({"/floors/{floors}"})
    public List<PropertyEntity> getPropertyByFloors(@PathVariable int floors){
        return this.propertyService.findByFloors(floors);
    }

    @GetMapping({"/capacity/{capacity}"})
    public List<PropertyEntity> getPropertyByCapacity(@PathVariable int capacity){
        return this.propertyService.findByCapacity(capacity);
    }

    @GetMapping({"/construction_date/{construction_date}"})
    public List<PropertyEntity> getPropertyByConstructionDate(@PathVariable Date construction_date){
        return this.propertyService.findByConstructionDate(construction_date);
    }

    @GetMapping({"/publish_date/{publish_date}"})
    public List<PropertyEntity> getPropertyByPublishDate(@PathVariable Date publish_date){
        return this.propertyService.findByPublishDate(publish_date);
    }

}
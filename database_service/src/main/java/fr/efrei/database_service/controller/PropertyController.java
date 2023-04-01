package fr.efrei.database_service.controller;

import fr.efrei.database_service.dto.PropertyDTO;
import fr.efrei.database_service.entity.PropertyEntity;
import fr.efrei.database_service.service.PropertyService;
import fr.efrei.database_service.tools.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public PropertyDTO createProperty(@RequestBody PropertyEntity property){
        return Mapper.convertToDto(this.propertyService.save(property), PropertyDTO.class);
    }

    @GetMapping("/{id}")
    public PropertyDTO getProperty(@PathVariable Long id){
        return Mapper.convertToDto(this.propertyService.findById(id), PropertyDTO.class);
    }

    @PutMapping("/{id}")
    public PropertyDTO updateProperty(@PathVariable Long id, @RequestBody PropertyEntity property){
        return Mapper.convertToDto(propertyService.update(id, property), PropertyDTO.class);
    }

    @DeleteMapping({"/{id}"})
    public void deleteProperty(@PathVariable Long id){
        this.propertyService.deleteById(id);
    }

    @GetMapping
    public List<PropertyDTO> getAllProperty(){
        return this.propertyService.findAll().stream().map(property -> Mapper.convertToDto(property, PropertyDTO.class)).collect(Collectors.toList());
    }

    @GetMapping({"/title/{title}"})
    public List<PropertyDTO> getPropertyByTitle(@PathVariable String title){
        return this.propertyService.findByTitle(title).stream().map(property -> Mapper.convertToDto(property, PropertyDTO.class)).collect(Collectors.toList());
    }

    @GetMapping({"/description/{description}"})
    public List<PropertyDTO> getPropertyByDescription(@PathVariable String description){
        return this.propertyService.findByDescription(description).stream().map(property -> Mapper.convertToDto(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/address/{address}"})
    public List<PropertyDTO> getPropertyByAddress(@PathVariable String address){
        return this.propertyService.findByAddress(address).stream().map(property -> Mapper.convertToDto(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/city/{city}"})
    public List<PropertyDTO> getPropertyByCity(@PathVariable String city){
        return this.propertyService.findByCity(city).stream().map(property -> Mapper.convertToDto(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/country/{country}"})
    public List<PropertyDTO> getPropertyByCountry(@PathVariable String country){
        return this.propertyService.findByCountry(country).stream().map(property -> Mapper.convertToDto(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/price/{price}"})
    public List<PropertyDTO> getPropertyByPrice(@PathVariable BigDecimal price){
        return this.propertyService.findByPrice(price).stream().map(property -> Mapper.convertToDto(property, PropertyDTO.class)).collect(Collectors.toList());
    }

    @GetMapping({"/owner_email/{email}"})
    public List<PropertyDTO> getPropertyByOwnerEmail(@PathVariable String email){
        return this.propertyService.findByOwnerEmail(email).stream().map(property -> Mapper.convertToDto(property, PropertyDTO.class)).collect(Collectors.toList());
    }
    @GetMapping({"/floors/{floors}"})
    public List<PropertyDTO> getPropertyByFloors(@PathVariable int floors){
        return this.propertyService.findByFloors(floors).stream().map(property -> Mapper.convertToDto(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/capacity/{capacity}"})
    public List<PropertyDTO> getPropertyByCapacity(@PathVariable int capacity){
        return this.propertyService.findByCapacity(capacity).stream().map(property -> Mapper.convertToDto(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/construction_date/{construction_date}"})
    public List<PropertyDTO> getPropertyByConstructionDate(@PathVariable Date construction_date){
        return this.propertyService.findByConstructionDate(construction_date).stream().map(property -> Mapper.convertToDto(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/publish_date/{publish_date}"})
    public List<PropertyDTO> getPropertyByPublishDate(@PathVariable Date publish_date){
        return this.propertyService.findByPublishDate(publish_date).stream().map(property -> Mapper.convertToDto(property, PropertyDTO.class)).collect(Collectors.toList());

    }

    @GetMapping({"/zip_code/{code}"})
    public List<PropertyDTO> getPropertyByZipCode(@PathVariable int code){
        return this.propertyService.findByZipCode(code).stream().map(property -> Mapper.convertToDto(property, PropertyDTO.class)).collect(Collectors.toList());
    }

}
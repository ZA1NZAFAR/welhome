package fr.efrei.database_service.service;

import fr.efrei.database_service.entity.PropertyEntity;
import fr.efrei.database_service.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Service
public class PropertyService implements CRUD<PropertyEntity, Long>{

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public PropertyEntity save(PropertyEntity property) {
        return propertyRepository.save(property);
    }

    @Override
    public PropertyEntity findById(Long id) {
        return propertyRepository.findById(id).orElse(null);
    }

    @Override
    public PropertyEntity update(Long id, PropertyEntity property) {

        property.setId(id);
        return propertyRepository.save(property);

    }

    @Override
    public void deleteById(Long id) {
        propertyRepository.deleteById(id);
    }

    public List<PropertyEntity> findAll() {
        return propertyRepository.findAll();
    }

    public List<PropertyEntity> findByTitle(String title){
        return propertyRepository.findByTitle(title);
    }

    public List<PropertyEntity> findByDescription(String description){
        return propertyRepository.findByDescription(description);
    }

    public List<PropertyEntity> findByAddress(String address){
        return propertyRepository.findByAddress(address);
    }

    public List<PropertyEntity> findByCity(String city){
        return propertyRepository.findByCity(city);
    }

    public List<PropertyEntity> findByCountry(String country){
        return propertyRepository.findByCountry(country);
    }

    public List<PropertyEntity> findByPrice(BigDecimal price){
        return propertyRepository.findByPrice(price);
    }

    public List<PropertyEntity> findByFloors(int floors){
        return propertyRepository.findByFloors(floors);
    }

    public List<PropertyEntity> findByCapacity(int capacity){
        return propertyRepository.findByCapacity(capacity);
    }

    public List<PropertyEntity> findByConstructionDate(Date contructionDate){
        return propertyRepository.findByConstructionDate(contructionDate);
    }

    public List<PropertyEntity> findByPublishDate(Date publishDate){
        return propertyRepository.findByPublishDate(publishDate);
    }

    public List<PropertyEntity> findByOwnerEmail(String email){
        return propertyRepository.findByOwnerEmail(email);
    }
    public List<PropertyEntity> findByZipCode(int zip_code){
        return propertyRepository.findByZipCode(zip_code);
    }

}

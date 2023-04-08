package fr.efrei.database_service.service;

import fr.efrei.database_service.entity.PropertyEntity;
import fr.efrei.database_service.exception.DatabaseExceptions;
import fr.efrei.database_service.repository.PropertyRepository;
import fr.efrei.database_service.tools.Tools;
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
        if (propertyRepository.findById(property.getId()).isPresent())
            throw new DatabaseExceptions.EntityAlreadyExistsException();
        if (property.getPublishDate()==null)
            property.setPublishDate(new Date(System.currentTimeMillis()));
        return propertyRepository.save(property);
    }

    @Override
    public PropertyEntity findById(Long id) {
        return propertyRepository.findById(id).orElseThrow(DatabaseExceptions.EntityNotFoundException::new);
    }

    @Override
    public PropertyEntity update(Long id, PropertyEntity updatedProperty) {
        PropertyEntity existingProperty = propertyRepository.findById(id).orElseThrow(DatabaseExceptions.EntityNotFoundException::new);
        if (!Tools.isNullOrEmpty(updatedProperty.getId()) && existingProperty.getId() != (updatedProperty.getId()))
            throw new DatabaseExceptions.BadRequestException("Id cannot be changed");

        // only update the fields that are not null and different from the existing property
        if (!Tools.isNullOrEmpty(updatedProperty.getTitle()))
            existingProperty.setTitle(updatedProperty.getTitle());
        if (!Tools.isNullOrEmpty(updatedProperty.getDescription()))
            existingProperty.setDescription(updatedProperty.getDescription());
        if (!Tools.isNullOrEmpty(updatedProperty.getPropertyCategory()))
            existingProperty.setPropertyCategory(updatedProperty.getPropertyCategory());
        if (!Tools.isNullOrEmpty(updatedProperty.getAddress()))
            existingProperty.setAddress(updatedProperty.getAddress());
        if (!Tools.isNullOrEmpty(updatedProperty.getCity()))
            existingProperty.setCity(updatedProperty.getCity());
        if (!Tools.isNullOrEmpty(updatedProperty.getState()))
            existingProperty.setState(updatedProperty.getState());
        if (!Tools.isNullOrEmpty(updatedProperty.getZipCode()))
            existingProperty.setZipCode(updatedProperty.getZipCode());
        if (!Tools.isNullOrEmpty(updatedProperty.getCountry()))
            existingProperty.setCountry(updatedProperty.getCountry());
        if (!Tools.isNullOrEmpty(updatedProperty.getConstructionDate()))
            existingProperty.setConstructionDate(updatedProperty.getConstructionDate());
        if (!Tools.isNullOrEmpty(updatedProperty.getFloors()))
            existingProperty.setFloors(updatedProperty.getFloors());
        if (!Tools.isNullOrEmpty(updatedProperty.getCapacity()))
            existingProperty.setCapacity(updatedProperty.getCapacity());
        if (!Tools.isNullOrEmpty(updatedProperty.getSurfaceArea()))
            existingProperty.setSurfaceArea(updatedProperty.getSurfaceArea());
        if (!Tools.isNullOrEmpty(updatedProperty.getPrice()))
            existingProperty.setPrice(updatedProperty.getPrice());
        if (!Tools.isNullOrEmpty(updatedProperty.getImageUrl1()))
            existingProperty.setImageUrl1(updatedProperty.getImageUrl1());
        if (!Tools.isNullOrEmpty(updatedProperty.getImageUrl2()))
            existingProperty.setImageUrl2(updatedProperty.getImageUrl2());
        if (!Tools.isNullOrEmpty(updatedProperty.getImageUrl3()))
            existingProperty.setImageUrl3(updatedProperty.getImageUrl3());

        return propertyRepository.save(existingProperty);
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

    public List<PropertyEntity> findByPropertyCategory(String category){
        return propertyRepository.findByPropertyCategory(category);
    }

    public List<PropertyEntity> findByAddress(String address){
        return propertyRepository.findByAddress(address);
    }

    public List<PropertyEntity> findByCity(String city){
        return propertyRepository.findByCity(city);
    }
    public List<PropertyEntity> findByState(String state){
        return propertyRepository.findByState(state);
    }


    public List<PropertyEntity> findByCountry(String country){
        return propertyRepository.findByCountry(country);
    }

    public List<PropertyEntity> findByPrice(BigDecimal price){
        return propertyRepository.findByPrice(price);
    }

    public List<PropertyEntity> findByPriceBetween(BigDecimal price1, BigDecimal price2){
        return propertyRepository.findByPriceBetween(price1, price2);
    }

    public List<PropertyEntity> findBySurfaceArea(float area){
        return propertyRepository.findBySurfaceArea(area);
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

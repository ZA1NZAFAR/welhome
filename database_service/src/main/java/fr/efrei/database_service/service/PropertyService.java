package fr.efrei.database_service.service;

import fr.efrei.database_service.entity.PropertyEntity;
import fr.efrei.database_service.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyService implements CRUD<PropertyEntity, Long>{

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public PropertyEntity save(PropertyEntity object) {
        return null;
    }

    @Override
    public PropertyEntity findById(Long id) {
        return null;
    }

    @Override
    public PropertyEntity update(Long id, PropertyEntity object) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}

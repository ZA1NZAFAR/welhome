package main.java.fr.efrei.database_service.service;

import main.java.fr.efrei.database_service.entity.Property;
import main.java.fr.efrei.database_service.repository.PropertyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public Long save(PropertyVO vO) {
        Property bean = new Property();
        BeanUtils.copyProperties(vO, bean);
        bean = propertyRepository.save(bean);
        return bean.getPropertyId();
    }

    public void delete(Long id) {
        propertyRepository.deleteById(id);
    }

    public void update(Long id, PropertyUpdateVO vO) {
        Property bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        propertyRepository.save(bean);
    }

    public PropertyDTO getById(Long id) {
        Property original = requireOne(id);
        return toDTO(original);
    }

    public Page<PropertyDTO> query(PropertyQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private PropertyDTO toDTO(Property original) {
        PropertyDTO bean = new PropertyDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Property requireOne(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}

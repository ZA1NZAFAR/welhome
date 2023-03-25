package main.java.fr.efrei.database_service.controller;

import main.java.fr.efrei.database_service.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public String save(@Valid @RequestBody PropertyVO vO) {
        return propertyService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete( @PathVariable("id") Long id) {
        propertyService.delete(id);
    }

    @PutMapping("/{id}")
    public void update( @PathVariable("id") Long id,
                       @Valid @RequestBody PropertyUpdateVO vO) {
        propertyService.update(id, vO);
    }

    @GetMapping("/{id}")
    public PropertyDTO getById(@Valid @NotNull @PathVariable("id") Long id) {
        return propertyService.getById(id);
    }

    @GetMapping
    public Page<PropertyDTO> query(@Valid PropertyQueryVO vO) {
        return propertyService.query(vO);
    }
}

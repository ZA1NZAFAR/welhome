package fr.efrei.backend.controllers;

import fr.efrei.backend.entities.Property;
import fr.efrei.backend.utils.ResponseGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}

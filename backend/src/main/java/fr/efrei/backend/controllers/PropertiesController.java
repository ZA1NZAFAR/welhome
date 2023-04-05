package fr.efrei.backend.controllers;

import fr.efrei.backend.entities.Profile;
import fr.efrei.backend.entities.Property;
import fr.efrei.backend.utils.ResponseGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}

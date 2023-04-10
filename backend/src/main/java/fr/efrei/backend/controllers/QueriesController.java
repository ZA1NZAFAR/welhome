package fr.efrei.backend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Queries", description = "This API allows to perform various complex queries")
@RequestMapping("/api/queries")
public class QueriesController {
    @Value("${databaseService.url}")
    private String URL;

    
}

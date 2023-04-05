package fr.efrei.backend.controllers;

import fr.efrei.backend.entities.Property;
import fr.efrei.backend.entities.Review;
import fr.efrei.backend.utils.ResponseGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/reviews")
public class ReviewsController {
    @Value("${databaseService.url}/reviews")
    private String URL;

    // Singleton instances of ResponseGenerator
    private ResponseGenerator<List<Review>> listGenerator;         // to send out JSON array in Response
    private ResponseGenerator<Review> generator;                   // to send out JSON object in Response

    @PostConstruct
    @Autowired
    private void init() {
        listGenerator = new ResponseGenerator<>();
        generator = new ResponseGenerator<>();
    }

    @GetMapping
    public ResponseEntity<List<Review>> getReviews() {
        ResponseEntity<List<Review>> result = listGenerator.buildRequest(URL, HttpMethod.GET, new ParameterizedTypeReference<List<Review>>() {});
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable String id) {
        ResponseEntity<Review> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.GET, new ParameterizedTypeReference<Review>() {});
        return result;
    }

    @PostMapping
    public ResponseEntity<Review> postReview(@RequestBody Review review) {
        ResponseEntity<Review> result = generator.buildRequest(URL, HttpMethod.POST, review, new ParameterizedTypeReference<Review>() {});
        return result;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> putReview(@PathVariable String id, @RequestBody Review review) {
        ResponseEntity<Review> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.PUT, review, new ParameterizedTypeReference<Review>() {});
        return result;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable String id) {
        ResponseEntity<Review> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.DELETE, new ParameterizedTypeReference<Review>() {});
        return result;
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<Review>> getReviewByRating(@PathVariable String rating) {
        ResponseEntity<List<Review>> result = listGenerator.buildRequest(URL.concat("/rating/" + rating), HttpMethod.GET, new ParameterizedTypeReference<List<Review>>() {});
        return result;
    }

    @GetMapping("/comment/{text}")
    public ResponseEntity<List<Review>> getReviewByComment(@PathVariable String text) {
        ResponseEntity<List<Review>> result = listGenerator.buildRequest(URL.concat("/comment/" + text), HttpMethod.GET, new ParameterizedTypeReference<List<Review>>() {});
        return result;
    }

    @GetMapping("/publish_date/{date}")
    public ResponseEntity<List<Review>> getReviewByPublishDate(@PathVariable String date) {
        ResponseEntity<List<Review>> result = listGenerator.buildRequest(URL.concat("/publish_date/" + date), HttpMethod.GET, new ParameterizedTypeReference<List<Review>>() {});
        return result;
    }
}
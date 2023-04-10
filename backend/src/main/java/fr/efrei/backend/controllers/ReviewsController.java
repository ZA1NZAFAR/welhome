package fr.efrei.backend.controllers;

import fr.efrei.backend.entities.Review;
import fr.efrei.backend.utils.ResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "Reviews", description = "This API allows to give an opinion on a specific reservation made by the renters")
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
    @Operation(summary = "This endpoint allows to retrieve all reviews of users")
    public ResponseEntity<List<Review>> getReviews() {
        ResponseEntity<List<Review>> result = listGenerator.buildRequest(URL, HttpMethod.GET, new ParameterizedTypeReference<List<Review>>() {});
        return result;
    }

    @GetMapping("/{id}")
    @Operation(summary = "This endpoint allows to retrieve a specific review about a reservation by providing its id")
    public ResponseEntity<Review> getReview(@PathVariable String id) {
        ResponseEntity<Review> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.GET, new ParameterizedTypeReference<Review>() {});
        return result;
    }

    @PostMapping
    @Operation(summary = "This endpoint allows to create a specific review about a reservation")
    @ApiResponse(responseCode = "201", description = "Creates a review")
    public ResponseEntity<Review> postReview(@RequestBody Review review) {
        ResponseEntity<Review> result = generator.buildRequest(URL, HttpMethod.POST, review, new ParameterizedTypeReference<Review>() {});
        return result;
    }

    @PutMapping("/{id}")
    @Operation(summary = "This endpoint allows to update a specific review about a reservation by providing its id if it exists, if not it creates a new review")
    @ApiResponse(responseCode = "200", description = "Updates a review")
    @ApiResponse(responseCode = "201", description = "Creates a review")
    public ResponseEntity<Review> putReview(@PathVariable String id, @RequestBody Review review) {
        ResponseEntity<Review> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.PUT, review, new ParameterizedTypeReference<Review>() {});
        return result;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "This endpoint allows to delete a specific review about a reservation by providing its id")
    @ApiResponse(responseCode = "200", description = "Deletes a review")
    @ApiResponse(responseCode = "404", description = "Review has not been found")
    public ResponseEntity<Review> deleteReview(@PathVariable String id) {
        ResponseEntity<Review> result = generator.buildRequest(URL.concat("/" + id), HttpMethod.DELETE, new ParameterizedTypeReference<Review>() {});
        return result;
    }

    @GetMapping("/rating/{rating}")
    @Operation(summary = "This endpoint allows to retrieve all reviews of a specific rating number")
    public ResponseEntity<List<Review>> getReviewByRating(@PathVariable String rating) {
        ResponseEntity<List<Review>> result = listGenerator.buildRequest(URL.concat("/rating/" + rating), HttpMethod.GET, new ParameterizedTypeReference<List<Review>>() {});
        return result;
    }

    @GetMapping("/comment/{text}")
    @Operation(summary = "This endpoint allows to retrieve all reviews of a specific comment")
    public ResponseEntity<List<Review>> getReviewByComment(@PathVariable String text) {
        ResponseEntity<List<Review>> result = listGenerator.buildRequest(URL.concat("/comment/" + text), HttpMethod.GET, new ParameterizedTypeReference<List<Review>>() {});
        return result;
    }

    @GetMapping("/publish_date/{date}")
    @Operation(summary = "This endpoint allows to retrieve all reviews of a specific publish date")
    public ResponseEntity<List<Review>> getReviewByPublishDate(@PathVariable String date) {
        ResponseEntity<List<Review>> result = listGenerator.buildRequest(URL.concat("/publish_date/" + date), HttpMethod.GET, new ParameterizedTypeReference<List<Review>>() {});
        return result;
    }

    @GetMapping("/property_id/{id}")
    @Operation(summary = "This endpoint allows to retrieve all reviews of a specific property id")
    public ResponseEntity<List<Review>> getReviewByPropertyId(@PathVariable String id) {
        ResponseEntity<List<Review>> result = listGenerator.buildRequest(URL.concat("/property_id/" + id), HttpMethod.GET, new ParameterizedTypeReference<List<Review>>() {});
        return result;
    }

    @GetMapping("/reviewer_email/{email}")
    @Operation(summary = "This endpoint allows to retrieve all reviews made by a specific reviewer")
    public ResponseEntity<List<Review>> getReviewByReviewerEmail(@PathVariable String email) {
        ResponseEntity<List<Review>> result = listGenerator.buildRequest(URL.concat("/reviewer_email/" + email), HttpMethod.GET, new ParameterizedTypeReference<List<Review>>() {});
        return result;
    }
}

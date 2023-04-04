package fr.efrei.database_service.controller;

import fr.efrei.database_service.dto.ReviewDTO;
import fr.efrei.database_service.entity.ReviewEntity;
import fr.efrei.database_service.service.ReviewService;
import fr.efrei.database_service.tools.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Review", description = "This API will allow to give an opinion on a specific reservation made by the renters.")
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    @Operation(summary = "This endPoint will allow to create a specific review about a reservation")
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewEntity reviewEntity) {
        ReviewEntity reviewToCreate = reviewService.save(reviewEntity);
        if(reviewToCreate == null){
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(Mapper.convertToDto(reviewToCreate,ReviewDTO.class));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "This endPoint will allow to display a specific review about a reservation by providing its id")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable long id) {
        ReviewEntity review = this.reviewService.findById(id);
        if(review == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(Mapper.convertToDto(review, ReviewDTO.class));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "This endPoint will allow to update a specific review about a reservation by providing its id")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable long id, @RequestBody ReviewEntity review) {
        ReviewEntity reviewToUpdate = reviewService.update(id, review);
        if(reviewToUpdate == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(Mapper.convertToDto(reviewToUpdate, ReviewDTO.class));
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "This endPoint will allow to delete a specific review about a reservation by providing its id")
    public ResponseEntity<ReviewDTO> deleteReview(@PathVariable long id) {
        try{
            this.reviewService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "This endPoint will allow to display all reviews of users")
    public List<ReviewDTO> getAllReviews() {
        return this.reviewService.findAll().stream().map(reviewEntity -> Mapper.convertToDto(reviewEntity, ReviewDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/rating/{rating}")
    @Operation(summary = "This endPoint will allow to display all reviews of a specific rating number")
    public List<ReviewDTO> getRating(@PathVariable int rating){
        return this.reviewService.findByRating(rating).stream().map(review -> Mapper.convertToDto(review, ReviewDTO.class)).collect(Collectors.toList());
    }
    @GetMapping("/comment/{text}")
    @Operation(summary = "This endPoint will allow to display all reviews of a specific comment")
    public List<ReviewDTO> getReviewText(@PathVariable String text){
        return this.reviewService.findByReviewText(text).stream().map(review -> Mapper.convertToDto(review, ReviewDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/publish_date/{date}")
    @Operation(summary = "This endPoint will allow to retrieve all reviews of a specific publish date")
    public List<ReviewDTO> getPublishDate(@PathVariable Date date){
        return this.reviewService.findByPublishDate(date).stream().map(review -> Mapper.convertToDto(review, ReviewDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/property_id/{id}")
    @Operation(summary = "This endPoint will allow to retrieve all reviews of a specific property id")
    public List<ReviewDTO> getPropertyId(@PathVariable int id){
        return this.reviewService.findByPropertyId(id).stream().map(review -> Mapper.convertToDto(review, ReviewDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/reviewer_email/{email}")
    @Operation(summary = "This endPoint will allow to retrieve all reviews made by a specific reviewer")
    public List<ReviewDTO> getReviewerEmail(@PathVariable String email){
        return this.reviewService.findByReviewerEmail(email).stream().map(review -> Mapper.convertToDto(review, ReviewDTO.class)).collect(Collectors.toList());
    }
}

package fr.efrei.database_service.controller;

import fr.efrei.database_service.dto.ReviewDTO;
import fr.efrei.database_service.entity.ReviewEntity;
import fr.efrei.database_service.service.ReviewService;
import fr.efrei.database_service.tools.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ReviewDTO createReview(@RequestBody ReviewEntity reviewEntity) {
        return Mapper.convertToDto(reviewService.save(reviewEntity), ReviewDTO.class);
    }
    @GetMapping("/{id}")
    public ReviewDTO getReview(@PathVariable long id) {
        return Mapper.convertToDto(reviewService.findById(id), ReviewDTO.class);
    }

    @PutMapping("/{id}")
    public ReviewDTO updateReview(@PathVariable long id, @RequestBody ReviewEntity reviewEntity) {
        return Mapper.convertToDto(reviewService.update(id, reviewEntity), ReviewDTO.class);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable long id) {
        this.reviewService.deleteById(id);
    }

    @GetMapping
    public List<ReviewDTO> getAllUsers() {
        return this.reviewService.findAll().stream().map(reviewEntity -> Mapper.convertToDto(reviewEntity, ReviewDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/rating/{rating}")
    public List<ReviewDTO> getRating(@PathVariable int rating){
        return this.reviewService.findByRating(rating).stream().map(review -> Mapper.convertToDto(review, ReviewDTO.class)).collect(Collectors.toList());
    }
    @GetMapping("/comment/{text}")
    public List<ReviewDTO> getReviewText(@PathVariable String text){
        return this.reviewService.findByReviewText(text).stream().map(review -> Mapper.convertToDto(review, ReviewDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/publish_date/{date}")
    public List<ReviewDTO> getPublishDate(@PathVariable Date date){
        return this.reviewService.findByPublishDate(date).stream().map(review -> Mapper.convertToDto(review, ReviewDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/property_id/{id}")
    public List<ReviewDTO> getPropertyId(@PathVariable int id){
        return this.reviewService.findByPropertyId(id).stream().map(review -> Mapper.convertToDto(review, ReviewDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/reviewer_email/{email}")
    public List<ReviewDTO> getReviewerEmail(@PathVariable String email){
        return this.reviewService.findByReviewerEmail(email).stream().map(review -> Mapper.convertToDto(review, ReviewDTO.class)).collect(Collectors.toList());
    }

}

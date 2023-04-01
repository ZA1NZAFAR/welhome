package fr.efrei.database_service.controller;

import fr.efrei.database_service.entity.ReviewEntity;
import fr.efrei.database_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ReviewEntity createReview(@RequestBody ReviewEntity reviewEntity) {
        return this.reviewService.save(reviewEntity);
    }
    @GetMapping("/{id}")
    public ReviewEntity getReview(@PathVariable long id) {
        return this.reviewService.findById(id);
    }

    @PutMapping("/{id}")
    public ReviewEntity updateReview(@PathVariable long id, @RequestBody ReviewEntity reviewEntity) {
        return reviewService.update(id, reviewEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable long id) {
        this.reviewService.deleteById(id);
    }

    @GetMapping
    public List<ReviewEntity> getAllUsers() {
        return this.reviewService.findAll();
    }

}

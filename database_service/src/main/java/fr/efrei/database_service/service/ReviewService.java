package fr.efrei.database_service.service;

import fr.efrei.database_service.entity.Review;
import fr.efrei.database_service.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public Review update(Long id, Review review) {
        review.setReviewId(id);
        return reviewRepository.save(review);
    }

    public List<Review> findByEmail(String email) {
        return null;
    }
}

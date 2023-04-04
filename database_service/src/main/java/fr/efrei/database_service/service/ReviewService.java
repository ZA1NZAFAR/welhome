package fr.efrei.database_service.service;

import fr.efrei.database_service.entity.ReviewEntity;
import fr.efrei.database_service.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ReviewService implements CRUD<ReviewEntity, Long> {

    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public ReviewEntity save(ReviewEntity object) {
        return reviewRepository.save(object);
    }

    @Override
    public ReviewEntity findById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public ReviewEntity update(Long id, ReviewEntity object) {
        object.setId(id);
        return reviewRepository.save(object);
    }

    @Override
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<ReviewEntity> findAll() {
        return reviewRepository.findAll();
    }

    public List<ReviewEntity> findByRating(float rating){
        return this.reviewRepository.findByRating(rating);
    }

    public List<ReviewEntity> findByReviewText(String text){
        return this.reviewRepository.findByReviewText(text);
    }

    public List<ReviewEntity> findByPublishDate(Date publish_date){
        return this.reviewRepository.findByPublishDate(publish_date);
    }

    public List<ReviewEntity> findByPropertyId(int property_id){
        return this.reviewRepository.findByPropertyId(property_id);
    }

    public List<ReviewEntity> findByReviewerEmail(String email){
        return this.reviewRepository.findByReviewerEmail(email);
    }

}

package fr.efrei.database_service.service;

import fr.efrei.database_service.entity.ReviewEntity;
import fr.efrei.database_service.exception.DatabaseExceptions;
import fr.efrei.database_service.repository.ReviewRepository;
import fr.efrei.database_service.tools.Tools;
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
        if (reviewRepository.findById(object.getId()).isPresent())
            throw new DatabaseExceptions.EntityAlreadyExistsException();
        if (object.getPublishDate()==null)
            object.setPublishDate(new Date(System.currentTimeMillis()));
        return reviewRepository.save(object);
    }

    @Override
    public ReviewEntity findById(Long id) {
        return reviewRepository.findById(id).orElseThrow(DatabaseExceptions.EntityNotFoundException::new);
    }

    @Override
    public ReviewEntity update(Long id, ReviewEntity updatedReview) {
        ReviewEntity existingReview = reviewRepository.findById(id).orElseThrow(DatabaseExceptions.EntityNotFoundException::new);
        if (!Tools.isNullOrEmpty(updatedReview.getId()) && existingReview.getId() != (updatedReview.getId()))
            throw new DatabaseExceptions.BadRequestException("Id cannot be changed");

        // only update the fields that are not null and different from the existing review
        if (!Tools.isNullOrEmpty(updatedReview.getRating()))
            existingReview.setRating(updatedReview.getRating());
        if (!Tools.isNullOrEmpty(updatedReview.getReviewText()))
            existingReview.setReviewText(updatedReview.getReviewText());
        if (!Tools.isNullOrEmpty(updatedReview.getPublishDate()))
            existingReview.setPublishDate(updatedReview.getPublishDate());
        if (!Tools.isNullOrEmpty(updatedReview.getPropertyId()))
            existingReview.setPropertyId(updatedReview.getPropertyId());
        if (!Tools.isNullOrEmpty(updatedReview.getReviewerEmail()))
            existingReview.setReviewerEmail(updatedReview.getReviewerEmail());
        if (!Tools.isNullOrEmpty(updatedReview.getImage()))
            existingReview.setImage(updatedReview.getImage());

        return reviewRepository.save(existingReview);
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

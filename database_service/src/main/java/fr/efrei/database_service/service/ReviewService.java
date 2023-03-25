package fr.efrei.database_service.service;

import fr.efrei.database_service.entity.ReviewEntity;
import fr.efrei.database_service.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService implements CRUD<ReviewEntity, Long> {

    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public ReviewEntity save(ReviewEntity object) {
        return null;
    }

    @Override
    public ReviewEntity findById(Long id) {
        return null;
    }

    @Override
    public ReviewEntity update(Long id, ReviewEntity object) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}

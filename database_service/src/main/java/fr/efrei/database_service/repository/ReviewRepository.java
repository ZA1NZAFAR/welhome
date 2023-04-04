package fr.efrei.database_service.repository;

import fr.efrei.database_service.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByRating(float rating);

    List<ReviewEntity> findByReviewText(String text);

    List<ReviewEntity> findByPublishDate(Date publish_date);

    List<ReviewEntity> findByPropertyId(float property_id);

    List<ReviewEntity> findByReviewerEmail(String email);

}

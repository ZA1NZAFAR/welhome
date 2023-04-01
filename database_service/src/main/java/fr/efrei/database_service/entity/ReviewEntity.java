package fr.efrei.database_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "review")
public class ReviewEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "rating")
    private float rating;
    @Basic
    @Column(name = "review_text")
    private String reviewText;
    @Basic
    @Column(name = "publish_date")
    private Date publishDate;
    @Basic
    @Column(name = "image")
    private String image;
    @Basic
    @Column(name = "property_id")
    private long propertyId;
    @Basic
    @Column(name = "reviewer_email")
    private String reviewerEmail;
    @Basic
    @Column(name = "comment")
    private String comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewEntity that = (ReviewEntity) o;
        return id == that.id && Float.compare(that.rating, rating) == 0 && propertyId == that.propertyId && Objects.equals(reviewText, that.reviewText) && Objects.equals(publishDate, that.publishDate) && Objects.equals(image, that.image) && Objects.equals(reviewerEmail, that.reviewerEmail) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating, reviewText, publishDate, image, propertyId, reviewerEmail, comment);
    }
}

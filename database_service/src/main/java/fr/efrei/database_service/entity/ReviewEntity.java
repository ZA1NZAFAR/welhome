package fr.efrei.database_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "review", schema = "public", catalog = "welhome")
public class ReviewEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "review_id")
    private long reviewId;
    @Basic
    @Column(name = "rating")
    private Float rating;
    @Basic
    @Column(name = "comment")
    private String comment;
    @Basic
    @Column(name = "property_id")
    private int propertyId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewEntity that = (ReviewEntity) o;
        return reviewId == that.reviewId && propertyId == that.propertyId && Objects.equals(rating, that.rating) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, rating, comment, propertyId);
    }
}

package fr.efrei.backend.entities;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class Review {
    private Long id;
    private Float rating;
    private String reviewText;
    private Date publishDate;
    private String image;
    private Long propertyId;
    private String reviewerEmail;
}

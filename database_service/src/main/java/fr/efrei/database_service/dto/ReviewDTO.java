package fr.efrei.database_service.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class ReviewDTO {
    private Long id;

    private Float rating;

    private String reviewText;

    private Date publishDate;

    private String image;

    private Long propertyId;

    private String reviewerEmail;

}

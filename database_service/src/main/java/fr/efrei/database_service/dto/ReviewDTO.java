package fr.efrei.database_service.dto;


import java.sql.Date;

public class ReviewDTO {
    private Long id;

    private Float rating;

    private String reviewText;

    private Date publishDate;

    private String image;

    private Long propertyId;

    private String reviewerEmail;

}

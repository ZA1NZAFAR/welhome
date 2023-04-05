package fr.efrei.backend.entities;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@ToString
public class Property {
    private Long id;
    private String title;
    private String description;
    private String propertyCategory;
    private String address;
    private String city;
    private Long zipCode;
    private String state;
    private String country;
    private BigDecimal price;
    private Float surfaceArea;
    private Long floors;
    private Long capacity;
    private Date constructionDate;
    private Date publishDate;
    private String ownerEmail;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
}

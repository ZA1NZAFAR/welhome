package fr.efrei.database_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "property")
public class PropertyEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "property_category")
    private String propertyCategory;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "city")
    private String city;
    @Basic
    @Column(name = "zip_code")
    private Integer zipCode;
    @Basic
    @Column(name = "state")
    private String state;
    @Basic
    @Column(name = "country")
    private String country;
    @Basic
    @Column(name = "price")
    private BigDecimal price;
    @Basic
    @Column(name = "surface_area")
    private float surfaceArea;
    @Basic
    @Column(name = "floors")
    private int floors;
    @Basic
    @Column(name = "capacity")
    private int capacity;
    @Basic
    @Column(name = "construction_date")
    private Date constructionDate;
    @Basic
    @Column(name = "publish_date")
    private Date publishDate;
    @Basic
    @Column(name = "owner_email")
    private String ownerEmail;
    @Basic
    @Column(name = "image_url_1")
    private String imageUrl_1;
    @Basic
    @Column(name = "image_url_2")
    private String imageUrl_2;
    @Basic
    @Column(name = "image_url_3")
    private String imageUrl_3;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyEntity that = (PropertyEntity) o;
        return id == that.id && Float.compare(that.surfaceArea, surfaceArea) == 0 && floors == that.floors && capacity == that.capacity && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(propertyCategory, that.propertyCategory) && Objects.equals(address, that.address) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(country, that.country) && Objects.equals(price, that.price) && Objects.equals(constructionDate, that.constructionDate) && Objects.equals(publishDate, that.publishDate) && Objects.equals(ownerEmail, that.ownerEmail) && Objects.equals(imageUrl_1, that.imageUrl_1) && Objects.equals(imageUrl_2, that.imageUrl_2) && Objects.equals(imageUrl_3, that.imageUrl_3) && Objects.equals(zipCode, that.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, propertyCategory, address, city, state, country, price, surfaceArea, floors, capacity, constructionDate, publishDate, ownerEmail, imageUrl_1, imageUrl_2, imageUrl_3, zipCode);
    }
}

package fr.efrei.database_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "property", schema = "public", catalog = "welhome")
public class PropertyEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "property_id")
    private long propertyId;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "price")
    private Float price;
    @Basic
    @Column(name = "city")
    private String city;
    @Basic
    @Column(name = "zip_code")
    private Integer zipCode;
    @Basic
    @Column(name = "country")
    private String country;
    @Basic
    @Column(name = "construction_date")
    private Date constructionDate;
    @Basic
    @Column(name = "area")
    private Float area;
    @Basic
    @Column(name = "capacity")
    private Integer capacity;
    @Basic
    @Column(name = "property_type")
    private String propertyType;
    @Basic
    @Column(name = "floors")
    private Integer floors;
    @Basic
    @Column(name = "publish_date")
    private Date publishDate;
    @Basic
    @Column(name = "email")
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyEntity that = (PropertyEntity) o;
        return propertyId == that.propertyId && Objects.equals(title, that.title) && Objects.equals(address, that.address) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(city, that.city) && Objects.equals(zipCode, that.zipCode) && Objects.equals(country, that.country) && Objects.equals(constructionDate, that.constructionDate) && Objects.equals(area, that.area) && Objects.equals(capacity, that.capacity) && Objects.equals(propertyType, that.propertyType) && Objects.equals(floors, that.floors) && Objects.equals(publishDate, that.publishDate) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyId, title, address, description, price, city, zipCode, country, constructionDate, area, capacity, propertyType, floors, publishDate, email);
    }
}

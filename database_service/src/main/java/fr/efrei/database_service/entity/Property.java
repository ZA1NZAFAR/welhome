package fr.efrei.database_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "property")
@Getter
@Setter
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private Long propertyId;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "zip_code")
    private Integer zipCode;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "construction_date")
    private LocalDate constructionDate;

    @Column(name = "area")
    private Double area;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "property_type", length = 100)
    private String propertyType;

    @Column(name = "floors")
    private Integer floors;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private Profile profile;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    // constructors, getters, and setters
}


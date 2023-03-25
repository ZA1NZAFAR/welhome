package fr.efrei.database_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "property")
@Getter
@Setter
public class Property implements Serializable {
    private static final long serialVersionUID = 1L;

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
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "price")
    private Double price;

    @Column(name = "city", length = 100)
    private String city;
    @Column(name = "state_province")
    private String stateProvince;

    @Column(name = "zip_code")
    private Integer zipCode;

    @Column(name = "country", length = 100)
    private String country;
    @Column(name = "latitude")
    private BigDecimal latitude;
    @Column(name = "longitude")
    private BigDecimal longitude;

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
    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private Profile profile;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    // constructors, getters, and setters
}


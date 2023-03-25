package fr.efrei.database_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "reservation")
@Getter
@Setter
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "total_price")
    private Double totalPrice;
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "confirmed_owner")
    private Boolean confirmedOwner;

    @Column(name = "confirmed_renter")
    private Boolean confirmedRenter;
    @Column(name = "property_id", nullable = false)
    private Long propertyId;
    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

}

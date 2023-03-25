package fr.efrei.database_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "reservation", schema = "public", catalog = "welhome")
public class ReservationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "reservation_id")
    private long reservationId;
    @Basic
    @Column(name = "start_date")
    private Date startDate;
    @Basic
    @Column(name = "end_date")
    private Date endDate;
    @Basic
    @Column(name = "total_price")
    private Float totalPrice;
    @Basic
    @Column(name = "confirmed_owner")
    private Boolean confirmedOwner;
    @Basic
    @Column(name = "confirmed_renter")
    private Boolean confirmedRenter;
    @Basic
    @Column(name = "property_id")
    private int propertyId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationEntity that = (ReservationEntity) o;
        return reservationId == that.reservationId && propertyId == that.propertyId && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(totalPrice, that.totalPrice) && Objects.equals(confirmedOwner, that.confirmedOwner) && Objects.equals(confirmedRenter, that.confirmedRenter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, startDate, endDate, totalPrice, confirmedOwner, confirmedRenter, propertyId);
    }
}

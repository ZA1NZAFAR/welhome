package fr.efrei.database_service.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "reservation", schema = "public", catalog = "welhome_dev")
public class ReservationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "property_id")
    private long propertyId;
    @Basic
    @Column(name = "renter_email")
    private String renterEmail;
    @Basic
    @Column(name = "start_date")
    private Date startDate;
    @Basic
    @Column(name = "end_date")
    private Date endDate;
    @Basic
    @Column(name = "confirmed_owner")
    private boolean confirmedOwner;
    @Basic
    @Column(name = "confirmed_renter")
    private boolean confirmedRenter;
    @Basic
    @Column(name = "total_price")
    private Float totalPrice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    public String getRenterEmail() {
        return renterEmail;
    }

    public void setRenterEmail(String renterEmail) {
        this.renterEmail = renterEmail;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isConfirmedOwner() {
        return confirmedOwner;
    }

    public void setConfirmedOwner(boolean confirmedOwner) {
        this.confirmedOwner = confirmedOwner;
    }

    public boolean isConfirmedRenter() {
        return confirmedRenter;
    }

    public void setConfirmedRenter(boolean confirmedRenter) {
        this.confirmedRenter = confirmedRenter;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationEntity that = (ReservationEntity) o;
        return id == that.id && propertyId == that.propertyId && confirmedOwner == that.confirmedOwner && confirmedRenter == that.confirmedRenter && Objects.equals(renterEmail, that.renterEmail) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(totalPrice, that.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, propertyId, renterEmail, startDate, endDate, confirmedOwner, confirmedRenter, totalPrice);
    }
}

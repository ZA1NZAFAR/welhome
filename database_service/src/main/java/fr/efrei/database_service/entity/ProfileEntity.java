package fr.efrei.database_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "first_name")
    private String firstName;
    @Basic
    @Column(name = "last_name")
    private String lastName;
    @Basic
    @Column(name = "birth_date")
    private Date birthDate;
    @Basic
    @Column(name = "phone_number")
    private String phoneNumber;
    @Basic
    @Column(name = "gender")
    private String gender;
    @Basic
    @Column(name = "registration_date")
    private Date registrationDate;
    @OneToMany(mappedBy = "ownerEmail")
    private List<PropertyEntity> properties;
    @OneToMany(mappedBy = "renterEmail")
    private List<ReservationEntity> reservations;
    @OneToMany(mappedBy = "reviewerEmail")
    private List<ReviewEntity> reviews;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileEntity that = (ProfileEntity) o;
        return Objects.equals(email, that.email) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(birthDate, that.birthDate) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(gender, that.gender) && Objects.equals(registrationDate, that.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName, birthDate, phoneNumber, gender, registrationDate);
    }
}

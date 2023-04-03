package fr.efrei.backend.entities;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class Reservation {
    private Long id;
    private Long propertyId;
    private String renterEmail;
    private Date startDate;
    private Date endDate;
    private Boolean confirmedOwner;
    private Boolean confirmedRenter;
}

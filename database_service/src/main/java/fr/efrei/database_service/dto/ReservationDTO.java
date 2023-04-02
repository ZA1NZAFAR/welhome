package fr.efrei.database_service.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
@Getter
@Setter
@ToString
public class ReservationDTO {
    private Long id;

    private Long propertyId;

    private String renterEmail;

    private Date startDate;

    private Date endDate;

    private Boolean confirmedOwner;

    private Boolean confirmedRenter;

}

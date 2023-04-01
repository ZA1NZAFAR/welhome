package fr.efrei.database_service.dto;


import java.sql.Date;

public class ReservationDTO {
    private Long id;

    private Long propertyId;

    private String renterEmail;

    private Date startDate;

    private Date endDate;

    private Boolean confirmedOwner;

    private Boolean confirmedRenter;

}

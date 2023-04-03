package fr.efrei.backend.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Setter
@Getter
@ToString
public class Profile {
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String phoneNumber;
    private String gender;
    private Date registrationDate;
}

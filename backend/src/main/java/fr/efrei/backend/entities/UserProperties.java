package fr.efrei.backend.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UserProperties {
    private Profile user;
    private List<Property> properties;
}

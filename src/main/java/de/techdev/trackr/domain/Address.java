package de.techdev.trackr.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;

/**
 * @author Moritz Schulze
 */
@Embeddable
@Data
public class Address {

    @NotEmpty
    private String street;

    @NotEmpty
    private String houseNumber;

    @NotEmpty
    private String zipCode;

    @NotEmpty
    private String city;

    @NotEmpty
    private String country;
}

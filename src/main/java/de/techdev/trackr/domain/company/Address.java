package de.techdev.trackr.domain.company;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * @author Moritz Schulze
 */
@Entity
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

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

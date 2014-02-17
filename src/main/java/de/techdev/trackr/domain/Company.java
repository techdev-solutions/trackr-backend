package de.techdev.trackr.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.Valid;

/**
 * @author Moritz Schulze
 */
@Data
@Entity
public class Company {

    @Id
    private Long id;

    @Version
    private Integer version;

    @NotEmpty
    private String name;

    @Valid
    private Address address;
}

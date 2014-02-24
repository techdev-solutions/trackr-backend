package de.techdev.trackr.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@Data
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    @NotNull
    @DecimalMin("0")
    @Column(unique = true)
    private Long companyId;

    @NotEmpty
    private String name;

    @Valid
    @NotNull
    private Address address;

    @OneToMany(mappedBy = "company")
    private List<ContactPerson> contactPersons;
}

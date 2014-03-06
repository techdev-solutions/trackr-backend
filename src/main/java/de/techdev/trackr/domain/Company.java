package de.techdev.trackr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@Data
@ToString(exclude = "contactPersons")
@EqualsAndHashCode(exclude = "contactPersons")
@Entity
@JsonIgnoreProperties({"debitorProjects"})
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

    @NotNull
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "company")
    private List<ContactPerson> contactPersons = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "company")
    private List<Project> projects = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    @RestResource(exported = false)
    private List<Project> debitorProjects = new ArrayList<>();
}

package de.techdev.trackr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents any employee of techdev.
 * @author Moritz Schulze
 */
@Data
@Entity
@ToString(exclude = {"workTimes"})
@JsonIgnoreProperties({"credential", "workTimes"})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private String phoneNumber;

    private String title;

    private BigDecimal salary;

    private BigDecimal hourlyCostRate;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Credential credential;

    @OneToMany(cascade = CascadeType.REMOVE)
    @RestResource(exported = false)
    private List<WorkTime> workTimes = new ArrayList<>();
}

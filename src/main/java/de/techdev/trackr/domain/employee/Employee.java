package de.techdev.trackr.domain.employee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.techdev.trackr.domain.common.FederalState;
import de.techdev.trackr.domain.employee.login.Credential;
import de.techdev.trackr.domain.project.BillableTime;
import de.techdev.trackr.domain.project.WorkTime;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents any employee of techdev.
 * @author Moritz Schulze
 */
@Data
@Entity
@ToString(exclude = {"workTimes", "billableTimes"})
@JsonIgnoreProperties({"credential", "workTimes", "billableTimes"})
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

    @Temporal(TemporalType.DATE)
    private Date joinDate;

    @Temporal(TemporalType.DATE)
    private Date leaveDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private FederalState federalState;

    private Float vacationEntitlement;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Credential credential;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "employee")
    @RestResource(exported = false)
    private List<WorkTime> workTimes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "employee")
    @RestResource(exported = false)
    private List<BillableTime> billableTimes = new ArrayList<>();

    public String fullName() {
        return getFirstName() + " " + getLastName();
    }
}

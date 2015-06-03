package de.techdev.trackr.domain.employee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.techdev.trackr.domain.common.FederalState;
import de.techdev.trackr.domain.company.Address;
import de.techdev.trackr.domain.employee.expenses.reports.Report;
import de.techdev.trackr.domain.employee.vacation.VacationRequest;
import de.techdev.trackr.domain.project.billtimes.BillableTime;
import de.techdev.trackr.domain.project.worktimes.WorkTime;
import de.techdev.trackr.domain.validation.constraints.EndAfterBegin;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents any employee of techdev.
 */
@Entity
@Getter
@Setter
@JsonIgnoreProperties({"credential", "workTimes", "billableTimes", "vacationRequests", "approvedRequests", "travelExpenseReports"})
@EndAfterBegin(begin = "joinDate", end = "leaveDate")
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

    @Column(unique = true)
    @Email
    @NotEmpty
    private String email;

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

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "employee")
    private List<WorkTime> workTimes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "employee")
    private List<BillableTime> billableTimes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "employee")
    private List<VacationRequest> vacationRequests;

    @OneToMany(mappedBy = "approver")
    private List<VacationRequest> approvedRequests;

    @OneToMany(mappedBy = "employee")
    private List<Report> travelExpenseReports;

    @OneToOne(orphanRemoval = true)
    private Address address;

    public String fullName() {
        return getFirstName() + " " + getLastName();
    }
}

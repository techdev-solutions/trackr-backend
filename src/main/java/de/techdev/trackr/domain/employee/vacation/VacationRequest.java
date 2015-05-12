package de.techdev.trackr.domain.employee.vacation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.validation.constraints.EndAfterBegin;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Entity
@Getter
@Setter
@EndAfterBegin(begin = "startDate", end = "endDate")
public class VacationRequest {

    public enum VacationRequestStatus {
        APPROVED, PENDING, REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    @ManyToOne
    @NotNull
    private Employee employee;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date endDate;

    private Integer numberOfDays;

    @Enumerated(EnumType.STRING)
    private VacationRequestStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date submissionTime;

    @ManyToOne
    private Employee approver;

    @JsonIgnore
    public boolean isApproved() {
        return status == VacationRequestStatus.APPROVED;
    }
}

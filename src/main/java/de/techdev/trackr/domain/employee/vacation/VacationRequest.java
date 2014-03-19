package de.techdev.trackr.domain.employee.vacation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.techdev.trackr.domain.employee.Employee;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Entity
@Data
public class VacationRequest {

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

    @Temporal(TemporalType.DATE)
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

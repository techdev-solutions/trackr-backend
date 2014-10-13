package de.techdev.trackr.domain.employee.vacation;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.validation.constraints.EndAfterBegin;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDate;

/**
 * @author Moritz Schulze
 */
@Entity
@Data
@EndAfterBegin(begin = "startDate", end = "endDate")
@ToString(exclude = {"employee"})
public class VacationRequest {

    public static enum VacationRequestStatus {
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

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private Integer numberOfDays;

    @Enumerated(EnumType.STRING)
    private VacationRequestStatus status;

    private Instant approvalDate;

    private Instant submissionTime;

    @ManyToOne
    private Employee approver;

    @JsonIgnore
    public boolean isApproved() {
        return status == VacationRequestStatus.APPROVED;
    }
}

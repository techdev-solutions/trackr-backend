package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.domain.employee.Employee;

import org.springframework.data.rest.core.config.Projection;

import java.time.Instant;
import java.time.LocalDate;

/**
 * @author Moritz Schulze
 */
@Projection(types = VacationRequest.class, name = "withEmployeeAndApprover")
public interface VacationRequestWithEmployeeAndApproverProjection {
    Long getId();

    Employee getEmployee();

    LocalDate getStartDate();

    LocalDate getEndDate();

    Integer getNumberOfDays();

    VacationRequest.VacationRequestStatus getStatus();

    Instant getApprovalDate();
    
    Instant getSubmissionTime();

    Employee getApprover();
}

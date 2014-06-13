package de.techdev.trackr.domain.employee.vacation;

import de.techdev.trackr.domain.employee.Employee;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Projection(types = VacationRequest.class, name = "withEmployeeAndApprover")
public interface VacationRequestWithEmployeeAndApproverProjection {
    Long getId();

    Employee getEmployee();

    Date getStartDate();

    Date getEndDate();

    Integer getNumberOfDays();

    VacationRequestStatus getStatus();

    Date getApprovalDate();

    Date getSubmissionTime();

    Employee getApprover();
}

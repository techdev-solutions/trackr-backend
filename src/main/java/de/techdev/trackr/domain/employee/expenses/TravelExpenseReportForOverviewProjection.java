package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.company.Company;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.project.Project;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@Projection(types = TravelExpenseReport.class, name = "overview")
public interface TravelExpenseReportForOverviewProjection {
    Long getId();

    Employee getEmployee();

    TravelExpenseReportStatus getStatus();

    List<TravelExpense> getExpenses();

    Date getSubmissionDate();

    Date getApprovalDate();

    Employee getApprover();

    Company getDebitor();

    Project getProject();
}

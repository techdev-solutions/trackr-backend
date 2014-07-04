package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.employee.Employee;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@Projection(types = TravelExpenseReport.class, name = "withEmployeeAndExpenses")
public interface TravelExpenseReportWithEmployeeAndTravelExpensesProjection {
    Long getId();

    Integer getVersion();

    Employee getEmployee();

    List<TravelExpense> getExpenses();

    TravelExpenseReportStatus getStatus();

    Date getSubmissionDate();
}

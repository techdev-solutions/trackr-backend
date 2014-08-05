package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.company.Company;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@Projection(types = TravelExpenseReport.class, name = "withExpensesAndDebitor")
public interface TravelExpenseReportWithExpensesAndDebitorProjection {
    Long getId();

    Integer getVersion();

    List<TravelExpense> getExpenses();

    TravelExpenseReportStatus getStatus();

    Date getSubmissionDate();

    Date getApprovalDate();

    Company getDebitor();
}

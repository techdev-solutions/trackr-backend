package de.techdev.trackr.domain.employee.expenses.reports;

import de.techdev.trackr.domain.company.Company;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.expenses.TravelExpense;
import de.techdev.trackr.domain.project.Project;

import org.springframework.data.rest.core.config.Projection;

import java.time.Instant;
import java.util.List;

/**
 * @author Moritz Schulze
 */
public class Projections {
    @Projection(types = Report.class, name = "overview")
    public static interface TravelExpenseReportForOverviewProjection {
        Long getId();

        Employee getEmployee();

        Report.Status getStatus();

        List<TravelExpense> getExpenses();

        Instant getSubmissionDate();

        Instant getApprovalDate();

        Employee getApprover();

        Company getDebitor();

        Project getProject();
    }

    @Projection(types = Report.class, name = "withEmployeeAndExpenses")
    public static interface TravelExpenseReportWithEmployeeAndTravelExpensesProjection {
        Long getId();

        Integer getVersion();

        Employee getEmployee();

        List<TravelExpense> getExpenses();

        Report.Status getStatus();

        Instant getSubmissionDate();
    }

    @Projection(types = Report.class, name = "withExpensesAndDebitor")
    public static interface TravelExpenseReportWithExpensesAndDebitorProjection {
        Long getId();

        Integer getVersion();

        List<TravelExpense> getExpenses();

        Report.Status getStatus();

        Instant getSubmissionDate();

        Instant getApprovalDate();

        Company getDebitor();
    }

    @Projection(types = Report.class, name = "withExpensesAndDebitorAndProject")
    public static interface TravelExpenseReportWithTravelExpensesProjection {

        Long getId();

        Integer getVersion();

        List<TravelExpense> getExpenses();

        Report.Status getStatus();

        Instant getSubmissionDate();

        Company getDebitor();

        Project getProject();
    }


}

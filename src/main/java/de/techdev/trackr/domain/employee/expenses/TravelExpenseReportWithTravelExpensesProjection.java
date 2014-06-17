package de.techdev.trackr.domain.employee.expenses;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * @author Moritz Schulze
 */
@Projection(types = TravelExpenseReport.class, name = "withExpenses")
public interface TravelExpenseReportWithTravelExpensesProjection {

    Long getId();

    Integer getVersion();

    List<TravelExpense> getExpenses();

    TravelExpenseReportStatus getStatus();

}

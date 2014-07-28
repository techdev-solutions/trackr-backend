package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.employee.Employee;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Projection(types = TravelExpenseReportComment.class, name = "withEmployee")
public interface TravelExpenseReportCommentWithEmployeeProjection {
    Long getId();

    String getText();

    Date getSubmissionDate();

    Employee getEmployee();
}

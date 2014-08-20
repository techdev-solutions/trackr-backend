package de.techdev.trackr.domain.employee.expenses.reports.comments;

import de.techdev.trackr.domain.employee.Employee;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Projection(types = Comment.class, name = "withEmployee")
public interface CommentWithEmployeeProjection {
    Long getId();

    String getText();

    Date getSubmissionDate();

    Employee getEmployee();
}

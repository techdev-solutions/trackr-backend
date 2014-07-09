package de.techdev.trackr.domain.employee.sickdays;

import de.techdev.trackr.domain.employee.Employee;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Projection(types = SickDays.class, name = "withEmployee")
public interface SickDaysWithEmployeeProjection {
    Long getId();

    Integer getVersion();

    Employee getEmployee();

    Date getStartDate();

    Date getEndDate();
}

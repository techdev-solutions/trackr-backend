package de.techdev.trackr.domain.employee.sickdays;

import de.techdev.trackr.domain.employee.Employee;

import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;

/**
 * @author Moritz Schulze
 */
@Projection(types = SickDays.class, name = "withEmployee")
public interface SickDaysWithEmployeeProjection {
    Long getId();

    Integer getVersion();

    Employee getEmployee();

    LocalDate getStartDate();

    LocalDate getEndDate();
}

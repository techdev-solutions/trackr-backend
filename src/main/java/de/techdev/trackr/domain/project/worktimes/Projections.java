package de.techdev.trackr.domain.project.worktimes;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.project.Project;

import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Moritz Schulze
 */
public class Projections {
    @Projection(types = WorkTime.class, name = "withEmployee")
    public static interface WorkTimeWithEmployeeProjection {
        Long getId();

        Integer getVersion();

        Employee getEmployee();

        LocalDate getDate();

        LocalTime getStartTime();

        LocalTime getEndTime();

        String getComment();
    }

    @Projection(types = WorkTime.class, name = "withProject")
    public interface WorkTimeWithProjectProjection {
        Long getId();

        Integer getVersion();

        Project getProject();

        LocalDate getDate();

        LocalTime getStartTime();

        LocalTime getEndTime();

        String getComment();
    }
}

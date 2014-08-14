package de.techdev.trackr.domain.project.worktimes;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.project.Project;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;
import java.util.Date;

/**
 * @author Moritz Schulze
 */
public class Projections {
    @Projection(types = WorkTime.class, name = "withEmployee")
    public static interface WorkTimeWithEmployeeProjection {
        Long getId();

        Integer getVersion();

        Employee getEmployee();

        Date getDate();

        Time getStartTime();

        Time getEndTime();

        String getComment();
    }

    @Projection(types = WorkTime.class, name = "withProject")
    public interface WorkTimeWithProjectProjection {
        Long getId();

        Integer getVersion();

        Project getProject();

        Date getDate();

        Time getStartTime();

        Time getEndTime();

        String getComment();
    }
}

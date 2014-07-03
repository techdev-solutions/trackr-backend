package de.techdev.trackr.domain.project;

import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
import de.techdev.trackr.domain.employee.Employee;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;
import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Projection(types = WorkTime.class, name = "withEmployee")
public interface WorkTimeWithEmployeeProjection {
    Long getId();

    Integer getVersion();

    Employee getEmployee();

    Date getDate();

    Time getStartTime();

    Time getEndTime();

    String getComment();
}

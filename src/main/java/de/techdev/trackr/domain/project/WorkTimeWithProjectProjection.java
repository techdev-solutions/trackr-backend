package de.techdev.trackr.domain.project;

import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;
import java.util.Date;

/**
 * Projection that adds the project to a worktime.
 * @author Moritz Schulze
 */
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

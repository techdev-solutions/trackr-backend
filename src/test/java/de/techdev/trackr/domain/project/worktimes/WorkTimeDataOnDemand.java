package de.techdev.trackr.domain.project.worktimes;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;
import de.techdev.trackr.domain.project.ProjectDataOnDemand;
import de.techdev.trackr.domain.project.worktimes.WorkTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.util.Date;

/**
 * @author Moritz Schulze
 */
public class WorkTimeDataOnDemand extends AbstractDataOnDemand<WorkTime> {

    @Override
    protected int getExpectedElements() {
        return 6;
    }

    @Autowired
    private ProjectDataOnDemand projectDataOnDemand;

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    @Override
    public WorkTime getNewTransientObject(int i) {
        WorkTime workTime = new WorkTime();
        workTime.setEmployee(employeeDataOnDemand.getRandomObject());
        workTime.setProject(projectDataOnDemand.getRandomObject());
        workTime.setDate(new Date());
        workTime.setStartTime(Time.valueOf("09:00:00"));
        workTime.setEndTime(Time.valueOf("17:00:00"));
        workTime.setComment("comment_" + i);
        return workTime;
    }
}

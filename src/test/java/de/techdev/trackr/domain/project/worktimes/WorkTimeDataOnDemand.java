package de.techdev.trackr.domain.project.worktimes;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;
import de.techdev.trackr.domain.project.ProjectDataOnDemand;

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
        workTime.setDate(LocalDate.now());
        workTime.setStartTime(LocalTime.parse("09:00:00"));
        workTime.setEndTime(LocalTime.parse("17:00:00"));
        workTime.setComment("comment_" + i);
        return workTime;
    }
}

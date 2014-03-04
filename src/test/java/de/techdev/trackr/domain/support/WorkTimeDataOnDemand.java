package de.techdev.trackr.domain.support;

import de.techdev.trackr.domain.WorkTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Component
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
        workTime.setStart(Time.valueOf("09:00:00"));
        workTime.setEnd(Time.valueOf("17:00:00"));
        workTime.setComment("comment_" + i);
        return workTime;
    }
}

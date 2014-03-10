package de.techdev.trackr.domain.support;

import de.techdev.trackr.domain.BillableTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Moritz Schulze
 */
@Component
public class BillableTimeDataOnDemand extends AbstractDataOnDemand<BillableTime> {

    @Autowired
    private ProjectDataOnDemand projectDataOnDemand;

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    @Override
    public BillableTime getNewTransientObject(int i) {
        BillableTime billableTime = new BillableTime();
        billableTime.setEmployee(employeeDataOnDemand.getRandomObject());
        billableTime.setProject(projectDataOnDemand.getRandomObject());
        billableTime.setDate(new Date());
        billableTime.setMinutes(i);
        return billableTime;
    }
}

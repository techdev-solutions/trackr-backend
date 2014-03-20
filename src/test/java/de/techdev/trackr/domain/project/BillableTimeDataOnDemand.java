package de.techdev.trackr.domain.project;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;
import de.techdev.trackr.util.LocalDateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

/**
 * @author Moritz Schulze
 */
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
        LocalDate localDate = LocalDate.now().with(ChronoField.DAY_OF_YEAR, (i % 356) + 1);
        billableTime.setDate(LocalDateUtil.fromLocalDate(localDate));
        billableTime.setMinutes(i);
        return billableTime;
    }
}

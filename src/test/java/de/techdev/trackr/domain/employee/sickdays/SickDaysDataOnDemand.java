package de.techdev.trackr.domain.employee.sickdays;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;
import de.techdev.trackr.util.LocalDateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

/**
 * @author Moritz Schulze
 */
public class SickDaysDataOnDemand extends AbstractDataOnDemand<SickDays> {

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    @Override
    public SickDays getNewTransientObject(int i) {

        SickDays sickDays = new SickDays();
        sickDays.setEmployee(employeeDataOnDemand.getRandomObject());
        LocalDate now = LocalDate.now();
        sickDays.setStartDate(LocalDateUtil.fromLocalDate(now));
        if (i % 2 == 0) {
            sickDays.setEndDate(LocalDateUtil.fromLocalDate(now.plusDays(i)));
        }
        return sickDays;
    }

}

package de.techdev.trackr.domain.employee;

import de.techdev.trackr.domain.AbstractDataOnDemand;
import de.techdev.trackr.domain.common.FederalState;

import java.math.BigDecimal;
import java.util.Date;

public class EmployeeDataOnDemand extends AbstractDataOnDemand<Employee> {

    @Override
    protected int getExpectedElements() {
        return 7;
    }

    @Override
    public Employee getNewTransientObject(int i) {
        Employee employee = new Employee();
        employee.setFirstName("firstName_" + i);
        employee.setLastName("lastName_" + i);
        employee.setEmail("email" + i + "@techdev.de");
        employee.setHourlyCostRate(BigDecimal.TEN.multiply(new BigDecimal(i)));
        employee.setPhoneNumber("phoneNumber_" + i);
        employee.setTitle("title_" + i);
        employee.setJoinDate(new Date());
        employee.setFederalState(FederalState.BERLIN);
        employee.setVacationEntitlement((float) i);
        return employee;
    }
}

package de.techdev.trackr.domain.support;

import de.techdev.trackr.domain.Employee;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Moritz Schulze
 */
@Component
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
        employee.setHourlyCostRate(BigDecimal.TEN.multiply(new BigDecimal(i)));
        employee.setPhoneNumber("phoneNumber_" + i);
        employee.setTitle("title_" + i);
        return employee;
    }
}

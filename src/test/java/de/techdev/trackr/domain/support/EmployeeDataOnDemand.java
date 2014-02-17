package de.techdev.trackr.domain.support;

import de.techdev.trackr.domain.Employee;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Moritz Schulze
 */
@Component
public class EmployeeDataOnDemand extends AbstractDataOnDemand<Employee> {

    public Employee getRandomEmployee() {
        init();
        Employee obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return repository.findOne(id);
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

    /**
     * The admin will always be in the repository, but we want it to have ten random elements.
     */
    @Override
    public void init() {
        repository.deleteAll();
        repository.flush();
        super.init();
    }
}

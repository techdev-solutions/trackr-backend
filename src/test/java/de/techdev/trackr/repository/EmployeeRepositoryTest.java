package de.techdev.trackr.repository;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.domain.Employee;
import de.techdev.trackr.domain.support.EmployeeDataOnDemand;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class EmployeeRepositoryTest extends TransactionalIntegrationTest {

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void findById() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        Employee one = employeeRepository.findOne(employee.getId());
        assertThat(one, isNotNull());
    }

    @Test
    public void findAll() throws Exception {
        employeeDataOnDemand.init();
        assertThat(employeeRepository.findAll(), isNotEmpty());
    }
}

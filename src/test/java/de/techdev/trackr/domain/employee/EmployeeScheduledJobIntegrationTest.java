package de.techdev.trackr.domain.employee;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.domain.employee.login.DeactivateEmployeesService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.echocat.jomon.testing.BaseMatchers.isFalse;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class EmployeeScheduledJobIntegrationTest extends TransactionalIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    private EmployeeScheduledJob employeeScheduledJob;

    @Before
    public void setUp() throws Exception {
        employeeScheduledJob = new EmployeeScheduledJob();
        DeactivateEmployeesService deactivateEmployeesService = new DeactivateEmployeesService();
        deactivateEmployeesService.setEmployeeRepository(employeeRepository);
        employeeScheduledJob.setDeactivateEmployeesService(deactivateEmployeesService);
    }

    @Test
    public void deactivateEmployeesWithLeaveDateToday() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        employee.setLeaveDate(new Date());
        employee.getCredential().setEnabled(true);
        employeeRepository.save(employee);

        employeeScheduledJob.deactivateEmployeesWithLeaveDateToday();

        Employee one = employeeRepository.findOne(employee.getId());
        assertThat(one.getCredential().getEnabled(), isFalse());
    }
}

package de.techdev.trackr.domain.employee;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.core.security.RemoveTokenService;
import de.techdev.trackr.domain.employee.login.DeactivateEmployeesService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

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

    @Autowired
    private RemoveTokenService removeTokenService;

    private EmployeeScheduledJob employeeScheduledJob;

    @Before
    public void setUp() throws Exception {
        employeeScheduledJob = new EmployeeScheduledJob();
        DeactivateEmployeesService deactivateEmployeesService = new DeactivateEmployeesService();
        deactivateEmployeesService.setEmployeeRepository(employeeRepository);
        deactivateEmployeesService.setRemoveTokenService(removeTokenService);
        employeeScheduledJob.setDeactivateEmployeesService(deactivateEmployeesService);
    }

    @Test
    public void deactivateEmployeesWithLeaveDateToday() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        employee.setJoinDate(LocalDate.now().minusDays(7));
        employee.setLeaveDate(LocalDate.now());
        employee.getCredential().setEnabled(true);
        employeeRepository.save(employee);

        employeeScheduledJob.deactivateEmployeesWithLeaveDateToday();

        Employee one = employeeRepository.findOne(employee.getId());
        assertThat(one.getCredential().getEnabled(), isFalse());
    }
}

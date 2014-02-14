package de.techdev.trackr.repository;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.domain.support.EmployeeDataOnDemand;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
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
    public void findAll() throws Exception {
        employeeDataOnDemand.init();
        assertThat(employeeRepository.findAll(), isNotEmpty());
    }
}

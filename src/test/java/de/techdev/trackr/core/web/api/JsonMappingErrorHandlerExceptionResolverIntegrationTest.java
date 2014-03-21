package de.techdev.trackr.core.web.api;

import de.techdev.trackr.core.web.MockMvcTest;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.EmployeeDataOnDemand;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class JsonMappingErrorHandlerExceptionResolverIntegrationTest extends MockMvcTest {

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    @Before
    public void setUp() throws Exception {
        employeeDataOnDemand.init();
    }

    @Test
    public void invalidPatchEmployee() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/employees/" + employee.getId())
                        .session(adminSession())
                        .content("{\"hourlyCostRate\": \"5a\"}"))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("errors[0].message", isNotEmpty())
               );
    }

}

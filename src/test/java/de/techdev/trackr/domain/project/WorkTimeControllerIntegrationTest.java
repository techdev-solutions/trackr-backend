package de.techdev.trackr.domain.project;

import de.techdev.trackr.core.web.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class WorkTimeControllerIntegrationTest extends MockMvcTest {

    @Autowired
    private WorkTimeDataOnDemand workTimeDataOnDemand;

    @Before
    public void setUp() throws Exception {
        workTimeDataOnDemand.init();
    }

    @Test
    public void findEmployeeMappingByProjectAndDateBetweenForbiddenForEmployee() throws Exception {
        mockMvc.perform(
                get("/workTimes/findEmployeeMappingByProjectAndDateBetween")
                        .param("project", "0")
                        .param("start", "2014-01-01")
                        .param("end", "2014-01-31")
                        .session(employeeSession()))
               .andExpect(status().isForbidden());
    }

    @Test
    public void findEmployeeMappingByProjectAndDateBetweenAllowedForSupervisor() throws Exception {
        mockMvc.perform(
                get("/workTimes/findEmployeeMappingByProjectAndDateBetween")
                        .param("project", "0")
                        .param("start", "2014-01-01")
                        .param("end", "2014-01-31")
                        .session(supervisorSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/json"));
    }
}

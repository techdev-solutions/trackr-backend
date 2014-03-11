package de.techdev.trackr.web.api;

import de.techdev.trackr.web.MockMvcTest;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class BillableTimeControllerIntegrationTest extends MockMvcTest {

    @Test
    public void findEmployeeMappingByProjectAndDateBetweenForbiddenForEmployee() throws Exception {
        mockMvc.perform(
                get("/billableTimes/findEmployeeMappingByProjectAndDateBetween")
                        .param("project", "0")
                        .param("start", "2014-01-01")
                        .param("end", "2014-01-31")
                        .session(employeeSession()))
               .andExpect(status().isForbidden());
    }

    @Test
    public void findEmployeeMappingByProjectAndDateBetweenAllowedForSupervisor() throws Exception {
        mockMvc.perform(
                get("/billableTimes/findEmployeeMappingByProjectAndDateBetween")
                        .param("project", "0")
                        .param("start", "2014-01-01")
                        .param("end", "2014-01-31")
                        .session(supervisorSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/json"));
    }
}

package de.techdev.trackr.domain.common;

import de.techdev.trackr.core.web.MockMvcTest;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class FederalStateControllerIntegrationTest extends MockMvcTest {

    @Test
    public void getAllFederalStates() throws Exception {
        mockMvc.perform(
                get("/federalStates")
                        .session(employeeSession()))
               .andExpect(status().isOk());
    }
}

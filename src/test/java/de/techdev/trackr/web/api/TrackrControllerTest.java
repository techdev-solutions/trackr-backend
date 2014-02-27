package de.techdev.trackr.web.api;

import de.techdev.trackr.web.MockMvcTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class TrackrControllerTest extends MockMvcTest {

    @Test
    public void principal() throws Exception {
        mockMvc.perform(
                get("/principal")
                        .session(adminSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}


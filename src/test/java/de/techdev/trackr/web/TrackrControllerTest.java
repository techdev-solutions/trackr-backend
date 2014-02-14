package de.techdev.trackr.web;

import org.junit.Test;
import org.springframework.http.MediaType;

import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class TrackrControllerTest extends MockMvcTest {

    /**
     * This test accesses the start page.
     * TODO: this is just an example test for mockMvc, it should be deleted as soon as there are real examples.
     * @throws Exception
     */
    @Test
    public void welcomePage() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    public void principal() throws Exception {
        mockMvc.perform(get("/principal").principal(() -> "admin"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}


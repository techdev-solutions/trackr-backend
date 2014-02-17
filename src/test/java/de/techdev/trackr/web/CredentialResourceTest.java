package de.techdev.trackr.web;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class CredentialResourceTest extends MockMvcTest {

    @Test
    public void findByEmail() throws Exception {
        mockMvc.perform(get("/credentials/search/findByEmail").param("email", "admin"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(standardContentType));
    }
}

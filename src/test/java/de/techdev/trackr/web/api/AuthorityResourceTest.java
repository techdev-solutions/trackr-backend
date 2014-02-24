package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Authority;
import de.techdev.trackr.domain.support.AuthorityDataOnDemand;
import de.techdev.trackr.web.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class AuthorityResourceTest extends MockMvcTest {

    @Autowired
    private AuthorityDataOnDemand authorityDataOnDemand;

    @Before
    public void setUp() throws Exception {
        authorityDataOnDemand.init();
    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(
                get("/authorities")
                        .session(basicSession()))
               .andExpect(status().isOk()).andExpect(content().contentType(standardContentType));
    }

    @Test
    public void findOne() throws Exception {
        Authority authority = authorityDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/authorities/" + authority.getId())
                        .session(basicSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType));
    }

    @Test
    public void postDisabled() throws Exception {
        mockMvc.perform(
                post("/authorities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(basicSession()).content("{}"))
               .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void putDisabled() throws Exception {
        Authority authority = authorityDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/authorities/" + authority.getId())
                        .session(basicSession())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
               .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void deleteDisabled() throws Exception {
        Authority authority = authorityDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/authorities/" + authority.getId())
                        .session(basicSession()))
               .andExpect(status().isMethodNotAllowed());
    }
}

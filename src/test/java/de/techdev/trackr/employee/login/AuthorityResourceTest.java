package de.techdev.trackr.employee.login;

import de.techdev.trackr.web.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                        .session(employeeSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType))
               .andExpect(jsonPath("_embedded.authorities[0].id", isNotNull()));
    }

    @Test
    public void findOne() throws Exception {
        Authority authority = authorityDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/authorities/" + authority.getId())
                        .session(employeeSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType))
               .andExpect(jsonPath("authority", is(authority.getAuthority())));
    }

    @Test
    public void postDisabled() throws Exception {
        mockMvc.perform(
                post("/authorities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(employeeSession()).content("{}"))
               .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void putDisabled() throws Exception {
        Authority authority = authorityDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/authorities/" + authority.getId())
                        .session(employeeSession())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
               .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void deleteDisabled() throws Exception {
        Authority authority = authorityDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/authorities/" + authority.getId())
                        .session(employeeSession()))
               .andExpect(status().isMethodNotAllowed());
    }
}

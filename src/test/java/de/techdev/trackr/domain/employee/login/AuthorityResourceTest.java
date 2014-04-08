package de.techdev.trackr.domain.employee.login;

import de.techdev.trackr.domain.AbstractDomainResourceTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.isAccessible;
import static de.techdev.trackr.domain.DomainResourceTestMatchers.isMethodNotAllowed;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Moritz Schulze
 */
public class AuthorityResourceTest extends AbstractDomainResourceTest<Authority> {

    @Override
    protected String getResourceName() {
        return "authorities";
    }

    @Test
    public void findAll() throws Exception {
        assertThat(root(employeeSession()), isAccessible());
    }

    @Test
    public void findOne() throws Exception {
        assertThat(one(employeeSession()), isAccessible());
    }

    @Test
    public void postDisabled() throws Exception {
        assertThat(create(employeeSession()), isMethodNotAllowed());
    }

    @Test
    public void putDisabled() throws Exception {
        assertThat(update(employeeSession()), isMethodNotAllowed());
    }

    @Test
    public void deleteDisabled() throws Exception {
        assertThat(remove(employeeSession()), isMethodNotAllowed());
    }

    @Test
    public void findByAuthorityDisabled() throws Exception {
        Authority authority = dataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/authorities/search/findByAuthority")
                        .session(employeeSession())
                        .param("authority", authority.getAuthority()))
               .andExpect(status().isMethodNotAllowed());
    }

    @Override
    protected String getJsonRepresentation(Authority item) {
        return "{}";
    }
}

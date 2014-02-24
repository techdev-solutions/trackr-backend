package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Authority;
import de.techdev.trackr.domain.Credential;
import de.techdev.trackr.domain.support.AuthorityDataOnDemand;
import de.techdev.trackr.domain.support.CredentialDataOnDemand;
import de.techdev.trackr.web.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.util.NestedServletException;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class CredentialResourceTest extends MockMvcTest {

    @Autowired
    private CredentialDataOnDemand credentialDataOnDemand;

    @Autowired
    private AuthorityDataOnDemand authorityDataOnDemand;

    @Before
    public void setUp() throws Exception {
        credentialDataOnDemand.init();
    }

    @Test
    public void findByEmail() throws Exception {
        Credential credentials = credentialDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/credentials/search/findByEmail")
                        .param("email", credentials.getEmail())
                        .session(basicSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType));
    }

    @Test
    public void addAuthorityToCredentials() throws Exception {
        Credential credentials = credentialDataOnDemand.getRandomObject();
        Authority authority = authorityDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/credentials/" + credentials.getId() + "/authorities")
                        .session(adminSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/authorities/" + authority.getId()))
               .andExpect(status().isNoContent());
    }

    @Test
    public void deleteAuthority() throws Exception {
        Credential credentials = credentialDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/credentials/" + credentials.getId() + "/authorities/0")
                        .session(adminSession()))
               .andExpect(status().isNoContent());
    }

    @Test
    public void addAuthorityNotAllowedForSupervisor() throws Exception {
        Credential credentials = credentialDataOnDemand.getRandomObject();
        Authority authority = authorityDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/credentials/" + credentials.getId() + "/authorities")
                        .session(supervisorSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/authorities/" + authority.getId()))
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteAuthorityNotAllowedForSupervisor() throws Exception {
        Credential credentials = credentialDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/credentials/" + credentials.getId() + "/authorities/0")
                        .session(supervisorSession()))
               .andExpect(status().isForbidden());
    }

}

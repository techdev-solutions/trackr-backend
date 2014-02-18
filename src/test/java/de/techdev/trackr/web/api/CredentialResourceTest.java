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
        Credential credentials = credentialDataOnDemand.getRandomCredentials();
        mockMvc.perform(get("/credentials/search/findByEmail").param("email", credentials.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(standardContentType));
    }

    @Test
    public void addAuthority() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(adminAuthentication());
        Credential credentials = credentialDataOnDemand.getRandomCredentials();
        Authority authority = authorityDataOnDemand.getRandomAuthority();
        mockMvc.perform(patch("/credentials/" + credentials.getId() + "/authorities")
                .header("Content-Type", "text/uri-list")
                .content("/authorities/" + authority.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteAuthority() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(adminAuthentication());
        Credential credentials = credentialDataOnDemand.getRandomCredentials();
        mockMvc.perform(delete("/credentials/" + credentials.getId() + "/authorities/0"))
                .andExpect(status().isNoContent());
    }

    //TODO: with mock mvc this does not return HTTP 403 which is bad.
    @Test(expected = NestedServletException.class)
    public void addAuthorityNotAllowedForSupervisor() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(supervisorAuthentication());
        Credential credentials = credentialDataOnDemand.getRandomCredentials();
        Authority authority = authorityDataOnDemand.getRandomAuthority();
        mockMvc.perform(patch("/credentials/" + credentials.getId() + "/authorities")
                .header("Content-Type", "text/uri-list")
                .content("/authorities/" + authority.getId()));
    }

    @Test(expected = NestedServletException.class)
    public void deleteAuthorityNotAllowedForSupervisor() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(supervisorAuthentication());
        Credential credentials = credentialDataOnDemand.getRandomCredentials();
        mockMvc.perform(delete("/credentials/" + credentials.getId() + "/authorities/0"));
    }

}

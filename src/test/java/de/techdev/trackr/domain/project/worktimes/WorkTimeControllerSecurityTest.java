package de.techdev.trackr.domain.project.worktimes;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractRestIntegrationTest;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static de.techdev.test.rest.DomainResourceTestMatchers.isAccessible;
import static de.techdev.test.rest.DomainResourceTestMatchers.isForbidden;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@OAuthRequest
public class WorkTimeControllerSecurityTest extends AbstractRestIntegrationTest {

    @Test
    public void findEmployeeMappingByProjectAndDateBetweenForbiddenForEmployee() throws Exception {
        ResponseEntity<String> response = restTemplate
                .getForEntity(host + "/workTimes/findEmployeeMappingByProjectAndDateBetween?project=0&start=2014-01-01&end=2014-01-31", String.class);
        assertThat(response, isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void findEmployeeMappingByProjectAndDateBetweenAllowedForSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate
                .getForEntity(host + "/workTimes/findEmployeeMappingByProjectAndDateBetween?project=0&start=2014-01-01&end=2014-01-31", String.class);
        assertThat(response, isAccessible());
    }
}

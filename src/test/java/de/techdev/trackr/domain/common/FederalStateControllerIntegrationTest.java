package de.techdev.trackr.domain.common;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractRestIntegrationTest;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static de.techdev.test.rest.DomainResourceTestMatchers.isAccessible;
import static org.junit.Assert.assertThat;

@OAuthRequest
public class FederalStateControllerIntegrationTest extends AbstractRestIntegrationTest {

    @Test
    public void getAllFederalStates() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/federalStates", String.class);
        assertThat(response, isAccessible());
    }
}

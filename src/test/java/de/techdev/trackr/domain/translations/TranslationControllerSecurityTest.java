package de.techdev.trackr.domain.translations;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractRestIntegrationTest;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static de.techdev.test.rest.DomainResourceTestMatchers.isAccessible;
import static org.junit.Assert.assertThat;

@OAuthRequest
public class TranslationControllerSecurityTest extends AbstractRestIntegrationTest {

    @Test
    public void testGetTranslationsIsAccessible() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/translations?locale=de", String.class);
        assertThat(response, isAccessible());
    }
}

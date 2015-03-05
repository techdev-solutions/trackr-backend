package de.techdev.trackr.domain.employee.addressbook;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractRestIntegrationTest;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static de.techdev.test.rest.DomainResourceTestMatchers.isAccessible;
import static org.junit.Assert.assertThat;

@OAuthRequest
public class AddressBookControllerSecurityTest extends AbstractRestIntegrationTest {

    @Test
    public void rootIsAccessible() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/address_book", String.class);
        assertThat(response, isAccessible());
    }
}
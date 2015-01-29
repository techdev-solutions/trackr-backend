package de.techdev.trackr.domain.employee.login;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractRestIntegrationTest;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static de.techdev.test.rest.DomainResourceTestMatchers.isAccessible;
import static org.junit.Assert.assertThat;

@OAuthRequest
@Sql("resourceTest.sql")
@Sql(value = "/de/techdev/trackr/domain/emptyDatabase.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PrincipalControllerSecurityTest extends AbstractRestIntegrationTest {

    @Test
    public void principal() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/principal", String.class);
        assertThat(response, isAccessible());
    }
}


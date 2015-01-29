package de.techdev.test.rest;

import de.techdev.test.InMemoryOAuth2ResourceServerConfiguration;
import de.techdev.test.oauth.OAuthTestExecutionListener;
import de.techdev.trackr.Trackr;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import javax.json.Json;
import javax.json.stream.JsonGeneratorFactory;

/**
 * Abstract test class to run tests that access the running REST API.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest(randomPort = true)
@SpringApplicationConfiguration(classes = {Trackr.class, InMemoryOAuth2ResourceServerConfiguration.class})
@ActiveProfiles({"in-memory-database", "test-oauth", "granular-security"})
@TestExecutionListeners(value = OAuthTestExecutionListener.class, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public abstract class AbstractRestIntegrationTest {

    @Value("${local.server.port}")
    private Integer serverPort;

    protected JsonGeneratorFactory jsonGeneratorFactory;
    protected RestTemplate restTemplate;
    protected String host;

    @Before
    public void setUpMvcFields() throws Exception {
        jsonGeneratorFactory = Json.createGeneratorFactory(null);
        restTemplate = new TestRestTemplate(OAuthTestExecutionListener.OAUTH_TOKEN_VALUE);
        host = "http://localhost:" + serverPort;
    }

}

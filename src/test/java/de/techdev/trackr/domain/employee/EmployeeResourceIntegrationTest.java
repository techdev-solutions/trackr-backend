package de.techdev.trackr.domain.employee;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import de.techdev.test.rest.AbstractRestIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@Sql(scripts = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EmployeeResourceIntegrationTest extends AbstractRestIntegrationTest {

    private EmployeeJsonGenerator jsonGenerator = new EmployeeJsonGenerator();

    @Autowired
    private SettingsRepository settingsRepository;

    @Test
    @OAuthRequest("ROLE_ADMIN")
    public void creatingAnEmployeeViaRestAlsoCreatesInitialLocaleSettings() throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>() {{
            put("Content-Type", singletonList("application/json; charset=utf-8"));
        }};

        Employee employee = jsonGenerator.getNewTransientObject(500);
        employee.setEmail("email@techdev.de");
        HttpEntity<String> request = new HttpEntity<>(jsonGenerator.getJsonRepresentation(employee), headers);
        restTemplate.exchange(host + "/employees", HttpMethod.POST, request, String.class);

        Settings settings = settingsRepository.findByTypeAndEmployee_Email(Settings.SettingsType.LOCALE, "email@techdev.de");
        assertThat(settings, is(notNullValue()));
        assertThat(settings.getValue(), is("de"));
    }
}

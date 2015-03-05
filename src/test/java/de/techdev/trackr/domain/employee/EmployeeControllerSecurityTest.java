package de.techdev.trackr.domain.employee;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractRestIntegrationTest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

import static de.techdev.test.rest.DomainResourceTestMatchers.isAccessible;
import static de.techdev.test.rest.DomainResourceTestMatchers.isForbidden;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Sql("resourceTest.sql")
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@OAuthRequest
public class EmployeeControllerSecurityTest extends AbstractRestIntegrationTest {

    @Test
    public void updateSelfViaPatch() throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>() {{
            put("Content-Type", asList("application/json; charset=utf-8"));
        }};
        HttpEntity<String> request = new HttpEntity<>("{\"firstName\": \"asd\", \"lastName\": \"asdf\", \"phoneNumber\": \"12345\"}", headers);

        ResponseEntity<String> response = restTemplate
                .exchange(host + "/employees/0/self", HttpMethod.PATCH, request, String.class);
        assertThat(response, isAccessible());
    }

    @Test
    public void updateSelfViaPatchReturns400() throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>() {{
            put("Content-Type", asList("application/json; charset=utf-8"));
        }};
        HttpEntity<String> request = new HttpEntity<>("{\"phoneNumber\": \"12345\"}", headers);

        ResponseEntity<String> response = restTemplate
                .exchange(host + "/employees/0/self", HttpMethod.PATCH, request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    @OAuthRequest(username = "someone.else@techdev.de")
    public void updateOtherViaPatchIsForbidden() throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>() {{
            put("Content-Type", asList("application/json; charset=utf-8"));
        }};
        HttpEntity<String> request = new HttpEntity<>("{\"phoneNumber\": \"12345\"}", headers);

        ResponseEntity<String> response = restTemplate
                .exchange(host + "/employees/0/self", HttpMethod.PATCH, request, String.class);
        assertThat(response, isForbidden());
    }

    @Test
    public void updateSelfViaPut() throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>() {{
            put("Content-Type", asList("application/json; charset=utf-8"));
        }};
        SelfEmployee selfEmployee = new SelfEmployee();
        selfEmployee.setFirstName("firstName");
        selfEmployee.setLastName("lastName");
        HttpEntity<String> request = new HttpEntity<>(generateEmployeeJson(selfEmployee), headers);

        ResponseEntity<String> response = restTemplate
                .exchange(host + "/employees/0/self", HttpMethod.PUT, request, String.class);
        assertThat(response, isAccessible());
    }

    @Test
    @OAuthRequest(username = "someone.else@techdev.de")
    public void updateOthersViaPutIsForbidden() throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>() {{
            put("Content-Type", asList("application/json; charset=utf-8"));
        }};
        SelfEmployee selfEmployee = new SelfEmployee();
        selfEmployee.setFirstName("firstName");
        selfEmployee.setLastName("lastName");
        HttpEntity<String> request = new HttpEntity<>(generateEmployeeJson(selfEmployee), headers);

        ResponseEntity<String> response = restTemplate
                .exchange(host + "/employees/0/self", HttpMethod.PUT, request, String.class);
        assertThat(response, isForbidden());
    }

    @Test
    public void accessSelf() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/employees/0/self", String.class);
        assertThat(response, isAccessible());
    }

    protected String generateEmployeeJson(SelfEmployee selfEmployee) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
          .write("firstName", selfEmployee.getFirstName())
          .write("lastName", selfEmployee.getLastName());
        if (selfEmployee.getPhoneNumber() != null) {
            jg.write("phoneNumber", selfEmployee.getPhoneNumber());
        }
        jg.writeEnd().close();
        return writer.toString();
    }
}

package de.techdev.trackr.domain.employee;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import de.techdev.test.rest.AbstractRestIntegrationTest;
import de.techdev.trackr.domain.company.Address;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.math.BigDecimal;

import static de.techdev.test.rest.DomainResourceTestMatchers.isAccessible;
import static de.techdev.test.rest.DomainResourceTestMatchers.isForbidden;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertThat;

@Sql("resourceTest.sql")
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@OAuthRequest
public class EmployeeControllerSecurityTest extends AbstractRestIntegrationTest {

    private SelfEmployee selfEmployee;

    @Before
    public void setUp() throws Exception {
        selfEmployee = new SelfEmployee();
        selfEmployee.setFirstName("Foo");
        selfEmployee.setLastName("Bar");
        selfEmployee.setId(0L);
        selfEmployee.setVersion(0);
        selfEmployee.setSalary(BigDecimal.TEN);
        selfEmployee.setTitle("title");
    }

    @Test
    public void updateSelfViaPut() throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>() {{
            put("Content-Type", singletonList("application/json; charset=utf-8"));
        }};
        HttpEntity<String> request = new HttpEntity<>(generateEmployeeJson(selfEmployee), headers);

        ResponseEntity<String> response = restTemplate
                .exchange(host + "/employees/0/self", HttpMethod.PUT, request, String.class);
        assertThat(response, isAccessible());
    }

    @Test
    @OAuthRequest(username = "someone.else@techdev.de")
    public void updateOtherViaPutIsForbidden() throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>() {{
            put("Content-Type", singletonList("application/json; charset=utf-8"));
        }};
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
          .write("lastName", selfEmployee.getLastName())
          .write("id", selfEmployee.getId())
          .write("version", selfEmployee.getVersion())
          .write("salary", selfEmployee.getSalary())
          .write("title", selfEmployee.getTitle());

        if (selfEmployee.getPhoneNumber() != null) {
            jg.write("phoneNumber", selfEmployee.getPhoneNumber());
        }

        Address address = selfEmployee.getAddress();
        if (address != null) {
            jg.writeStartObject("address")
                .write("id", address.getId())
                .write("version", address.getVersion())
                .write("street", address.getStreet())
                .write("houseNumber", address.getHouseNumber())
                .write("zipCode", address.getZipCode())
                .write("city", address.getCity())
                .write("country", address.getCountry())
            .writeEnd();
        }

        jg.writeEnd().close();
        return writer.toString();
    }
}

package de.techdev.trackr.domain.employee.login;

import de.techdev.trackr.domain.AbstractDomainResourceTest;
import de.techdev.trackr.domain.common.FederalState;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.EmployeeRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.isForbidden;
import static de.techdev.trackr.domain.DomainResourceTestMatchers.isNoContent;
import static de.techdev.trackr.domain.DomainResourceTestMatchers.isUpdated;
import static java.util.Arrays.asList;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Moritz Schulze
 */
public class CredentialResourceTest extends AbstractDomainResourceTest<Credential> {

    @Autowired
    private AuthorityDataOnDemand authorityDataOnDemand;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    protected String getResourceName() {
        return "credentials";
    }

    @Test
    public void findByEmail() throws Exception {
        Credential credentials = dataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/credentials/search/findByEmail")
                        .param("email", credentials.getEmail())
                        .session(employeeSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(STANDARD_CONTENT_TYPE))
               .andExpect(jsonPath("_embedded.credentials[0].email", is(credentials.getEmail())))
               .andExpect(jsonPath("_embedded.credentials[0].enabled", is(credentials.getEnabled())));
    }

    @Test
    public void addAuthorityToCredentials() throws Exception {
        Authority authority = authorityDataOnDemand.getRandomObject();
        assertThat(updateLink(adminSession(), "authorities", "/authorities/" + authority.getId()), isNoContent());
    }

    @Test
    public void deleteAuthority() throws Exception {
        Credential credentials = dataOnDemand.getRandomObject();
        assertThat(removeUrl(adminSession(), "/credentials/" + credentials.getId() + "/authorities/0"), isNoContent());
    }

    @Test
    public void addAuthorityNotAllowedForSupervisor() throws Exception {
        Authority authority = authorityDataOnDemand.getRandomObject();
        assertThat(updateLink(supervisorSession(), "authorities", "/authorities/" + authority.getId()), isForbidden());
    }

    @Test
    public void deleteAuthorityNotAllowedForSupervisor() throws Exception {
        Credential credentials = dataOnDemand.getRandomObject();
        assertThat(removeUrl(supervisorSession(), "/credentials/" + credentials.getId() + "/authorities/0"), isForbidden());
    }

    @Test
    public void putAllowedForAdmins() throws Exception {
        assertThat(update(adminSession()), isUpdated());
    }

    /**
     * Because of the 1:1 id mapping for credential and employees we have to create everything ourselves and cannot use the data on demand objects.
     * @return JSON representation of a new Credential object.
     */
    private String getNewCredentialJson() {
        Credential credential = new Credential();
        credential.setEmail("email_500@techdev.de");
        credential.setEnabled(false);
        credential.setAuthorities(asList(new Authority("ROLE_TEST")));
        credential.setLocale("en");
        Employee employee = new Employee();
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setFederalState(FederalState.BERLIN);
        employeeRepository.saveAndFlush(employee);
        credential.setEmployee(employee);
        return getJsonRepresentation(credential);
    }

    @Test
    public void postAllowedForAdmins() throws Exception {
        String json = getNewCredentialJson();
        mockMvc.perform(
                post("/credentials")
                        .session(adminSession())
                        .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("id", isNotNull()));
    }

    @Test
    public void deleteAllowedForAdmins() throws Exception {
        assertThat(remove(adminSession()), isNoContent());
    }

    @Test
    public void putForbiddenForSupervisors() throws Exception {
        assertThat(update(supervisorSession()), isForbidden());
    }

    @Test
    public void postForbiddenForSupervisors() throws Exception {
        String json = getNewCredentialJson();
        mockMvc.perform(
                post("/credentials")
                        .session(supervisorSession())
                        .content(json))
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteForbiddenForSupervisors() throws Exception {
        assertThat(remove(supervisorSession()), isForbidden());
    }

    @Test
    public void findByAuthoritiesDisabled() throws Exception {
        mockMvc.perform(
                get("/credentials/search/findByAuthorities")
                        .session(adminSession())
                        .param("authority", "/authorities/1"))
               .andExpect(status().isNotFound());
    }

    @Override
    protected String getJsonRepresentation(Credential credential) {
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = jsonGeneratorFactory.createGenerator(writer);
        JsonGenerator jg = jsonGenerator
                .writeStartObject()
                .write("email", credential.getEmail())
                .write("enabled", credential.getEnabled())
                .write("employee", "/api/employees/" + credential.getEmployee().getId())
                .write("locale", credential.getLocale());
        if(credential.getId() != null) {
            jg.write("id", credential.getId());
        }

        jg.writeEnd().close();
        return writer.toString();
    }
}

package de.techdev.trackr.domain.employee.login;

import de.techdev.trackr.core.web.MockMvcTest;
import de.techdev.trackr.domain.common.FederalState;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

import static java.util.Arrays.asList;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Moritz Schulze
 */
public class CredentialResourceTest extends MockMvcTest {

    @Autowired
    private CredentialDataOnDemand credentialDataOnDemand;

    @Autowired
    private AuthorityDataOnDemand authorityDataOnDemand;

    @Before
    public void setUp() throws Exception {
        credentialDataOnDemand.init();
    }

    @Test
    public void findByEmail() throws Exception {
        Credential credentials = credentialDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/credentials/search/findByEmail")
                        .param("email", credentials.getEmail())
                        .session(employeeSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType))
               .andExpect(jsonPath("_embedded.credentials[0].email", is(credentials.getEmail())))
               .andExpect(jsonPath("_embedded.credentials[0].enabled", is(credentials.getEnabled())));
    }

    @Test
    public void addAuthorityToCredentials() throws Exception {
        Credential credentials = credentialDataOnDemand.getRandomObject();
        Authority authority = authorityDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/credentials/" + credentials.getId() + "/authorities")
                        .session(adminSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/authorities/" + authority.getId()))
               .andExpect(status().isNoContent());
    }

    @Test
    public void deleteAuthority() throws Exception {
        Credential credentials = credentialDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/credentials/" + credentials.getId() + "/authorities/0")
                        .session(adminSession()))
               .andExpect(status().isNoContent());
    }

    @Test
    public void addAuthorityNotAllowedForSupervisor() throws Exception {
        Credential credentials = credentialDataOnDemand.getRandomObject();
        Authority authority = authorityDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/credentials/" + credentials.getId() + "/authorities")
                        .session(supervisorSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/authorities/" + authority.getId()))
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteAuthorityNotAllowedForSupervisor() throws Exception {
        Credential credentials = credentialDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/credentials/" + credentials.getId() + "/authorities/0")
                        .session(supervisorSession()))
               .andExpect(status().isForbidden());
    }

    @Test
    public void putAllowedForAdmins() throws Exception {
        Credential credential = credentialDataOnDemand.getRandomObject();
        String json = getCredentialJson(credential);
        mockMvc.perform(
                put("/credentials/" + credential.getId())
                        .session(adminSession())
                        .content(json))
               .andExpect(status().isOk());
    }

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void postAllowedForAdmins() throws Exception {
        //Because of the 1:1 id mapping for credential and employees we have to create everything ourselves and cannot use the data on demand objects.
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
        String json = getCredentialJson(credential);
        mockMvc.perform(
                post("/credentials")
                        .session(adminSession())
                        .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("id", isNotNull()));
    }

    @Test
    public void deleteAllowedForAdmins() throws Exception {
        Credential credential = credentialDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/credentials/" + credential.getId())
                        .session(adminSession()))
               .andExpect(status().isNoContent());
    }

    @Test
    public void putForbiddenForSupervisors() throws Exception {
        Credential credential = credentialDataOnDemand.getRandomObject();
        String json = getCredentialJson(credential);
        mockMvc.perform(
                put("/credentials/" + credential.getId())
                        .session(supervisorSession())
                        .content(json))
               .andExpect(status().isForbidden());
    }

    @Test
    public void postForbiddenForSupervisors() throws Exception {
        //Because of the 1:1 id mapping for credential and employees we have to create everything ourselves and cannot use the data on demand objects.
        Credential credential = new Credential();
        credential.setEmail("email_500@techdev.de");
        credential.setEnabled(false);
        credential.setLocale("en");
        credential.setAuthorities(asList(new Authority("ROLE_TEST")));
        Employee employee = new Employee();
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setFederalState(FederalState.BERLIN);
        employeeRepository.saveAndFlush(employee);
        credential.setEmployee(employee);
        String json = getCredentialJson(credential);
        mockMvc.perform(
                post("/credentials")
                        .session(supervisorSession())
                        .content(json))
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteForbiddenForSupervisors() throws Exception {
        Credential credential = credentialDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/credentials/" + credential.getId())
                        .session(supervisorSession()))
               .andExpect(status().isForbidden());
    }

    protected String getCredentialJson(Credential credential) {
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

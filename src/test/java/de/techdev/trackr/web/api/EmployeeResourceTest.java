package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Employee;
import de.techdev.trackr.domain.support.CredentialDataOnDemand;
import de.techdev.trackr.domain.support.EmployeeDataOnDemand;
import de.techdev.trackr.web.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Moritz Schulze
 */
public class EmployeeResourceTest extends MockMvcTest {

    @Autowired
    private EmployeeDataOnDemand employeeDataOnDemand;

    @Autowired
    private CredentialDataOnDemand credentialDataOnDemand;

    @Before
    public void setUp() throws Exception {
        employeeDataOnDemand.init();
    }

    /**
     * Employees are not listable by not supervisors or admins
     *
     * @throws Exception
     */
    @Test
    public void rootNotAllowedForEmployees() throws Exception {
        mockMvc.perform(
                get("/employees")
                        .session(employeeSession()))
               .andExpect(status().isForbidden());
    }

    /**
     * Supervisors may list employees.
     *
     * @throws Exception
     */
    @Test
    public void rootAllowedForSupervisors() throws Exception {
        mockMvc.perform(
                get("/employees")
                        .session(supervisorSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType))
               .andExpect(jsonPath("_embedded.employees[0].id", isNotNull()));
    }

    /**
     * Supervisors may access one employee.
     *
     * @throws Exception
     */
    @Test
    public void oneIsAllowedForSupervisor() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/employees/" + employee.getId())
                        .session(supervisorSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType))
               .andExpect(jsonPath("id", is(employee.getId().intValue())));
    }

    /**
     * An employee may access him/herself.
     *
     * @throws Exception
     */
    @Test
    public void oneIsAllowedForSelf() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/employees/" + employee.getId())
                        .session(employeeSession(employee.getId())))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType))
               .andExpect(jsonPath("id", is(employee.getId().intValue())));
    }

    /**
     * An employee may not access other employees.
     *
     * @throws Exception
     */
    @Test
    public void oneIsForbiddenForOtherEmployee() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/employees/" + employee.getId())
                        .session(employeeSession(employee.getId() + 1)))
               .andExpect(status().isForbidden());
    }

    /**
     * Credentials are accessible for supervisors.
     *
     * @throws Exception
     */
    @Test
    public void getCredentialAllowedForSupervisor() throws Exception {
        Employee employee = credentialDataOnDemand.getRandomObject().getEmployee();
        mockMvc.perform(
                get("/employees/" + employee.getId() + "/credential")
                        .session(supervisorSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType))
               .andExpect(jsonPath("email", is(employee.getCredential().getEmail())));
    }

    /**
     * An employee may access his own credentials.
     *
     * @throws Exception
     */
    @Test
    public void getCredentialAllowedForSelf() throws Exception {
        Employee employee = credentialDataOnDemand.getRandomObject().getEmployee();
        mockMvc.perform(
                get("/employees/" + employee.getId() + "/credential")
                        .session(employeeSession(employee.getId())))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType))
               .andExpect(jsonPath("email", is(employee.getCredential().getEmail())));
    }

    /**
     * An employee may not access other employees credentials.
     *
     * @throws Exception
     */
    @Test
    public void getCredentialForbiddenForOther() throws Exception {
        Employee employee = credentialDataOnDemand.getRandomObject().getEmployee();
        mockMvc.perform(
                get("/employees/" + employee.getId() + "/credential")
                        .session(employeeSession(employee.getId() + 1)))
               .andExpect(status().isForbidden());
    }

    /**
     * An admin may create employees.
     *
     * @throws Exception
     */
    @Test
    public void postAllowedForAdmins() throws Exception {
        Employee employee = employeeDataOnDemand.getNewTransientObject(500);
        mockMvc.perform(
                post("/employees")
                        .session(adminSession())
                        .content(generateEmployeeJson(employee)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("id", isNotNull()));
    }

    /**
     * An supervisor may edit employees.
     *
     * @throws Exception
     */
    @Test
    public void putAllowedForSupervisors() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/employees/" + employee.getId())
                        .session(supervisorSession())
                        .content(generateEmployeeJson(employee)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("id", is(employee.getId().intValue())));
    }

    /**
     * An supervisor may edit employees via patch.
     *
     * @throws Exception
     */
    @Test
    public void patchAllowedForSupervisors() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                patch("/employees/" + employee.getId())
                        .session(supervisorSession())
                        .content("{\"firstName\": \"Test\"}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("firstName", is("Test")));
    }

    /**
     * An admin may delete employees.
     *
     * @throws Exception
     */
    @Test
    public void deleteAllowedForAdmins() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/employees/" + employee.getId())
                        .session(adminSession()))
               .andExpect(status().isNoContent());
    }

    /**
     * A supervisor can not create employees.
     *
     * @throws Exception
     */
    @Test
    public void postForbiddenForSupervisor() throws Exception {
        Employee employee = employeeDataOnDemand.getNewTransientObject(500);
        mockMvc.perform(
                post("/employees")
                        .session(supervisorSession())
                        .content(generateEmployeeJson(employee)))
               .andExpect(status().isForbidden());
    }

    /**
     * A employee may not edit an employee entity.
     *
     * @throws Exception
     */
    @Test
    public void putForbiddenForEmployee() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/employees/" + employee.getId())
                        .session(employeeSession(employee.getId()))
                        .content(generateEmployeeJson(employee)))
               .andExpect(status().isForbidden());
    }

    /**
     * A supervisor can not delete employees.
     *
     * @throws Exception
     */
    @Test
    public void deleteForbiddenForSupervisor() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/employees/" + employee.getId())
                        .session(supervisorSession()))
               .andExpect(status().isForbidden());
    }

    /**
     * The credentials reference is not changeable.
     *
     * @throws Exception
     */
    @Test
    public void changeCredentialForbidden() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/employees/" + employee.getId() + "/credential")
                        .session(adminSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/credentials/0"))
               .andExpect(status().isForbidden());
    }

    /**
     * The credentials reference is not deletable.
     *
     * @throws Exception
     */
    @Test
    public void deleteCredentialForbidden() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/employees/" + employee.getId() + "/credential")
                        .session(adminSession()))
               .andExpect(status().isForbidden());
    }

    protected String generateEmployeeJson(Employee employee) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
          .write("firstName", employee.getFirstName())
          .write("lastName", employee.getLastName())
          .write("hourlyCostRate", employee.getHourlyCostRate())
          .write("salary", employee.getSalary())
          .write("title", employee.getTitle());

        if (employee.getPhoneNumber() != null) {
            jg.write("phoneNumber", employee.getPhoneNumber());
        }

        if (employee.getCredential() != null) {
            jg.write("credential", "/api/credentials/" + employee.getCredential().getId());
        }
        if (employee.getId() != null) {
            jg.write("id", employee.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }
}

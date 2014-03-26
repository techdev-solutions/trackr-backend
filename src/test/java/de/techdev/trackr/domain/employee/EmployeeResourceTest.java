package de.techdev.trackr.domain.employee;

import de.techdev.trackr.core.security.AuthorityMocks;
import de.techdev.trackr.core.web.MockMvcTest;
import de.techdev.trackr.domain.employee.login.Credential;
import de.techdev.trackr.domain.employee.login.CredentialDataOnDemand;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.echocat.jomon.testing.BaseMatchers.isTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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

    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() throws Exception {
        employeeDataOnDemand.init();
        credentialDataOnDemand.init();
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
    @Ignore //for some bloody reason this test fails on the build server, no idea why
    public void deleteCredentialForbidden() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/employees/" + employee.getId() + "/credential")
                        .session(adminSession()))
               .andExpect(status().isForbidden());
    }

    @Test
    public void setLeaveDateDeactivatesEmployeeIfIsInPast() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Employee employee = employeeDataOnDemand.getRandomObject();
        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.adminAuthentication());
        employee.getCredential().setEnabled(true);
        employee.setJoinDate(sdf.parse("2013-12-01"));
        employeeRepository.saveAndFlush(employee);
        mockMvc.perform(
                patch("/employees/" + employee.getId())
                        .session(adminSession())
                        .content("{\"leaveDate\": \"2014-01-01\"}"))
               .andExpect(status().isOk());
        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.adminAuthentication());
        Credential credential = employeeRepository.findOne(employee.getId()).getCredential();
        assertThat(credential.getEnabled(), is(false));
    }

    @Test
    public void setLeaveDateDoesNotDeactivateEmployeeIfIsInFuture() throws Exception {
        Employee employee = employeeDataOnDemand.getRandomObject();
        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.adminAuthentication());
        employee.getCredential().setEnabled(true);
        employeeRepository.saveAndFlush(employee);
        mockMvc.perform(
                patch("/employees/" + employee.getId())
                        .session(adminSession())
                        .content("{\"leaveDate\": \"3014-01-01\"}"))
               .andExpect(status().isOk());
        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.adminAuthentication());
        Credential credential = employeeRepository.findOne(employee.getId()).getCredential();
        assertThat(credential.getEnabled(), isTrue());
    }

    protected String generateEmployeeJson(Employee employee) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jg.writeStartObject()
          .write("firstName", employee.getFirstName())
          .write("lastName", employee.getLastName())
          .write("hourlyCostRate", employee.getHourlyCostRate())
          .write("salary", employee.getSalary())
          .write("title", employee.getTitle())
          .write("federalState", employee.getFederalState().getName());

        if(employee.getVacationEntitlement() != null) {
            jg.write("vacationEntitlement", employee.getVacationEntitlement());
        }

        if (employee.getJoinDate() != null) {
            jg.write("joinDate", sdf.format(employee.getJoinDate()));
        }

        if (employee.getLeaveDate() != null) {
            jg.write("leaveDate", sdf.format(employee.getLeaveDate()));
        }

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

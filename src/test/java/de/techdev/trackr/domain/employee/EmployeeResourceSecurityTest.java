package de.techdev.trackr.domain.employee;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import static de.techdev.test.rest.DomainResourceTestMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Sql("resourceTest.sql")
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@OAuthRequest("ROLE_SUPERVISOR")
public class EmployeeResourceSecurityTest extends AbstractDomainResourceSecurityTest {

    @Override
    protected String getResourceName() {
        return "employees";
    }

    @Test
    @OAuthRequest
    public void rootNotAllowedForEmployees() throws Exception {
        assertThat(root(), isForbidden());
    }

    @Test
    public void rootAllowedForSupervisors() throws Exception {
        assertThat(root(), isAccessible());
    }

    @Test
    public void oneIsAllowedForSupervisor() throws Exception {
        assertThat(one(0L), isAccessible());
    }

    @Test
    @OAuthRequest(username = "employee@techdev.de")
    public void oneIsAllowedForSelf() throws Exception {
        assertThat(one(0L), isAccessible());
    }

    @Test
    @OAuthRequest(username = "someone.else@techdev.de")
    public void oneIsForbiddenForOtherEmployee() throws Exception {
        assertThat(one(0L), isForbidden());
    }

//    @Test
//    public void postAllowedForAdmins() throws Exception {
//        assertThat(create(adminSession()), isCreated());
//    }

//    @Test
//    public void putAllowedForSupervisors() throws Exception {
//        assertThat(update(supervisorSession()), isUpdated());
//    }

//    @Test
//    public void patchAllowedForSupervisors() throws Exception {
//        assertThat(updateViaPatch(supervisorSession(), "{\"firstName\": \"Test\"}"), isUpdated());
//    }

    @Test
    @OAuthRequest("ROLE_ADMIN")
    public void deleteAllowedForAdmins() throws Exception {
        assertThat(remove(0L), isNoContent());
    }

//    @Test
//    public void postForbiddenForSupervisor() throws Exception {
//        assertThat(create(supervisorSession()), isForbidden());
//    }

//    @Test
//    public void putForbiddenForEmployee() throws Exception {
//        assertThat(update(employee -> employeeSession(employee.getEmail())), isForbidden());
//    }

    @Test
    public void deleteForbiddenForSupervisor() throws Exception {
        assertThat(remove(0L), isForbidden());
    }

    protected String getJsonRepresentation(Employee employee) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jg.writeStartObject()
          .write("firstName", employee.getFirstName())
          .write("lastName", employee.getLastName())
          .write("hourlyCostRate", employee.getHourlyCostRate())
          .write("salary", employee.getSalary())
          .write("email", employee.getEmail())
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

        if (employee.getId() != null) {
            jg.write("id", employee.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }
}

package de.techdev.trackr.domain.employee;

import de.techdev.trackr.domain.AbstractDomainResourceTest;
import org.junit.Test;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmployeeResourceTest extends AbstractDomainResourceTest<Employee> {

    @Override
    protected String getResourceName() {
        return "employees";
    }

    /**
     * Employees are not listable by not supervisors or admins
     *
     * @throws Exception
     */
    @Test
    public void rootNotAllowedForEmployees() throws Exception {
        assertThat(root(employeeSession()), isForbidden());
    }

    /**
     * Supervisors may list employees.
     *
     * @throws Exception
     */
    @Test
    public void rootAllowedForSupervisors() throws Exception {
        assertThat(root(supervisorSession()), isAccessible());
    }

    /**
     * Supervisors may access one employee.
     *
     * @throws Exception
     */
    @Test
    public void oneIsAllowedForSupervisor() throws Exception {
        assertThat(one(supervisorSession()), isAccessible());
    }

    /**
     * An employee may access him/herself.
     *
     * @throws Exception
     */
    @Test
    public void oneIsAllowedForSelf() throws Exception {
        assertThat(one(employee -> employeeSession(employee.getEmail())), isAccessible());
    }

    /**
     * An employee may not access other employees.
     *
     * @throws Exception
     */
    @Test
    public void oneIsForbiddenForOtherEmployee() throws Exception {
        assertThat(one(employee -> employeeSession(employee.getEmail() + 1)), isForbidden());
    }

    /**
     * An admin may create employees.
     *
     * @throws Exception
     */
    @Test
    public void postAllowedForAdmins() throws Exception {
        assertThat(create(adminSession()), isCreated());
    }

    /**
     * An supervisor may edit employees.
     *
     * @throws Exception
     */
    @Test
    public void putAllowedForSupervisors() throws Exception {
        assertThat(update(supervisorSession()), isUpdated());
    }

    /**
     * An supervisor may edit employees via patch.
     *
     * @throws Exception
     */
    @Test
    public void patchAllowedForSupervisors() throws Exception {
        assertThat(updateViaPatch(supervisorSession(), "{\"firstName\": \"Test\"}"), isUpdated());
    }

    /**
     * An admin may delete employees.
     *
     * @throws Exception
     */
    @Test
    public void deleteAllowedForAdmins() throws Exception {
        assertThat(remove(adminSession()), isNoContent());
    }

    /**
     * A supervisor can not create employees.
     *
     * @throws Exception
     */
    @Test
    public void postForbiddenForSupervisor() throws Exception {
        assertThat(create(supervisorSession()), isForbidden());
    }

    /**
     * A employee may not edit an employee entity.
     *
     * @throws Exception
     */
    @Test
    public void putForbiddenForEmployee() throws Exception {
        assertThat(update(employee -> employeeSession(employee.getEmail())), isForbidden());
    }

    /**
     * A supervisor can not delete employees.
     *
     * @throws Exception
     */
    @Test
    public void deleteForbiddenForSupervisor() throws Exception {
        assertThat(remove(supervisorSession()), isForbidden());
    }

    @Override
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

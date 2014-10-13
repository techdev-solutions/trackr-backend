package de.techdev.trackr.domain.employee.sickdays;

import de.techdev.trackr.domain.AbstractDomainResourceTest;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import javax.json.stream.JsonGenerator;

import java.io.StringWriter;
import java.util.function.Function;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.*;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class SickDaysResourceTest extends AbstractDomainResourceTest<SickDays> {

    private Function<SickDays, MockHttpSession> sameEmployeeSessionProvider = sickDays -> employeeSession(sickDays.getEmployee().getId());
    private Function<SickDays, MockHttpSession> otherEmployeeSessionProvider = sickDays -> employeeSession(sickDays.getEmployee().getId() + 1);

    @Override
    protected String getResourceName() {
        return "sickDays";
    }

    @Test
    public void rootIsAccessibleForAdmin() throws Exception {
        assertThat(root(adminSession()), isMethodNotAllowed());
    }

    @Test
    public void rootIsForbiddenForSupervisor() throws Exception {
        assertThat(root(supervisorSession()), isMethodNotAllowed());
    }

    @Test
    public void oneIsAllowedForEmployee() throws Exception {
        assertThat(one(sameEmployeeSessionProvider), isAccessible());
    }

    @Test
    public void oneIsForbiddenForOther() throws Exception {
        assertThat(one(otherEmployeeSessionProvider), isForbidden());
    }

    @Test
    public void createIsAllowedForEmployee() throws Exception {
        assertThat(create(sameEmployeeSessionProvider), isCreated());
    }

    @Test
    @Ignore
    // Not testable at the moment since it returns 400 instead of 403 (it can't convert the JSON because the employee is also restricted).
    public void createIsForbiddenForOther() throws Exception {
        assertThat(create(otherEmployeeSessionProvider), isForbidden());
    }

    @Test
    public void deleteIsAllowedForAdmin() throws Exception {
        assertThat(remove(adminSession()), isNoContent());
    }

    @Test
    public void deleteIsForbiddenForSupervisor() throws Exception {
        assertThat(remove(supervisorSession()), isForbidden());
    }

    @Test
    public void updateIsAllowedForEmployee() throws Exception {
        assertThat(update(sameEmployeeSessionProvider), isUpdated());
    }

    @Test
    @Ignore
    // Not testable at the moment since it returns 400 instead of 403 (it can't convert the JSON because the employee is also restricted).
    public void updateIsForbiddenForOther() throws Exception {
        assertThat(update(otherEmployeeSessionProvider), isForbidden());
    }

    @Test
    public void findByEmployeeIsAllowedForEmployee() throws Exception {
        SickDays sickDays = dataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/sickDays/search/findByEmployee")
                        .session(employeeSession(sickDays.getEmployee().getId()))
                        .param("employee", sickDays.getEmployee().getId().toString())
        )
                .andExpect(status().isOk());
    }

    @Test
    @Ignore
    // Not testable at the moment since it returns 400 instead of 403 (it can't convert the JSON because the employee is also restricted).
    public void findByEmployeeIsForbiddenForOther() throws Exception {
        SickDays sickDays = dataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/sickDays/search/findByEmployee")
                        .session(employeeSession(sickDays.getEmployee().getId() + 1))
                        .param("employee", sickDays.getEmployee().getId().toString())
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void findByStartDateBetweenOrEndDateBetweenIsAllowedForAdmin() throws Exception {
        mockMvc.perform(
                get("/sickDays/search/findByStartDateBetweenOrEndDateBetween")
                        .session(adminSession())
                        .param("startLower", "2014-07-01")
                        .param("startHigher", "2014-07-31")
                        .param("endLower", "2014-07-08")
                        .param("endHigher", "2014-08-09")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void findByStartDateBetweenOrEndDateBetweenIsForbiddenForSupervisor() throws Exception {
        mockMvc.perform(
                get("/sickDays/search/findByStartDateBetweenOrEndDateBetween")
                        .session(supervisorSession())
                        .param("startLower", "2014-07-01")
                        .param("startHigher", "2014-07-31")
                        .param("endLower", "2014-07-08")
                        .param("endHigher", "2014-08-09")
        )
                .andExpect(status().isForbidden());
    }

    @Override
    protected String getJsonRepresentation(SickDays sickDays) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
                .write("startDate", ISO_LOCAL_DATE.format(sickDays.getStartDate()));

        if (sickDays.getEndDate() != null) {
            jg.write("endDate", ISO_LOCAL_DATE.format(sickDays.getEndDate()));
        }

        if (sickDays.getEmployee() != null) {
            jg.write("employee", "/api/employees/" + sickDays.getEmployee().getId());
        }

        if (sickDays.getId() != null) {
            jg.write("id", sickDays.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }
}

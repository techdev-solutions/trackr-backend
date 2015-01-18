package de.techdev.trackr.domain.project.billtimes;

import de.techdev.trackr.domain.AbstractDomainResourceTest;
import de.techdev.trackr.domain.project.billtimes.BillableTime;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BillableTimeResourceTest extends AbstractDomainResourceTest<BillableTime> {

    private final Function<BillableTime, MockHttpSession> sameEmployeeSessionProvider;

    public BillableTimeResourceTest() {
        sameEmployeeSessionProvider = (BillableTime bt) -> employeeSession(bt.getEmployee().getEmail());
    }

    @Override
    protected String getResourceName() {
        return "billableTimes";
    }

    @Test
    public void rootNotExported() throws Exception {
        assertThat(root(employeeSession()), isMethodNotAllowed());
    }

    @Test
    public void oneAllowedForSupervisor() throws Exception {
        assertThat(one(supervisorSession()), isAccessible());
    }

    @Test
    public void oneForbiddenForEmployee() throws Exception {
        assertThat(one(employeeSession()), isForbidden());
    }

    @Test
    public void createAllowedForSupervisor() throws Exception {
        assertThat(create(supervisorSession()), isCreated());
    }

    @Test
    public void createForbiddenForEmployee() throws Exception {
        assertThat(create(sameEmployeeSessionProvider), isForbidden());
    }

    @Test
    public void deleteAllowedForSupervisor() throws Exception {
        assertThat(remove(supervisorSession()), isNoContent());
    }

    @Test
    public void deleteForbiddenForEmployee() throws Exception {
        assertThat(remove(employeeSession()), isForbidden());
    }

    @Test
    public void updateAllowedForSupervisor() throws Exception {
        assertThat(update(supervisorSession()), isUpdated());
    }

    /**
     * TODO: this fails with HTTP 400 because findOne is annotated with @PreAuthorize and spring-data-rest doesn't forward the AccessDeniedException correctly
     */
    @Test
    @Ignore
    public void updateForbiddenForEmployee() throws Exception {
        assertThat(update(employeeSession()), isForbidden());
    }

    @Test
    public void deleteEmployeeForbidden() throws Exception {
        BillableTime billableTime = dataOnDemand.getRandomObject();
        assertThat(removeUrl(adminSession(), "/billableTimes/" + billableTime.getId() + "/employee"), isForbidden());
    }

    @Test
    public void deleteProjectForbidden() throws Exception {
        BillableTime billableTime = dataOnDemand.getRandomObject();
        assertThat(removeUrl(adminSession(), "/billableTimes/" + billableTime.getId() + "/project"), isForbidden());
    }

    @Test
    public void updateEmployeeAllowedForSupervisor() throws Exception {
        assertThat(updateLink(supervisorSession(), "employee", "/employees/0"), isNoContent());
    }

    @Test
    public void updateProjectAllowedForSupervisor() throws Exception {
        assertThat(updateLink(supervisorSession(), "project", "/projects/0"), isNoContent());
    }

    @Test
    public void updateEmployeeForbiddenForEmployee() throws Exception {
        assertThat(updateLink(sameEmployeeSessionProvider, "employee", "/employees/0"), isForbidden());
    }

    @Test
    public void updateProjectForbiddenForEmployee() throws Exception {
        assertThat(updateLink(sameEmployeeSessionProvider, "project", "/projects/0"), isForbidden());
    }

    @Test
    public void findByDateBetweenAllowedForAdmin() throws Exception {
        mockMvc.perform(
                get("/billableTimes/search/findByDateBetween")
                        .session(adminSession())
                        .param("start", String.valueOf(new Date().getTime()))
                        .param("end", String.valueOf(new Date().getTime()))
        )
                .andExpect(status().isOk());
    }

    @Test
    public void findByDateBetweenForbiddenForSupervisor() throws Exception {
        mockMvc.perform(
                get("/billableTimes/search/findByDateBetween")
                        .session(supervisorSession())
                        .param("start", String.valueOf(new Date().getTime()))
                        .param("end", String.valueOf(new Date().getTime()))
        )
                .andExpect(status().isForbidden());
    }

    @Override
    protected String getJsonRepresentation(BillableTime billableTime) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jg.writeStartObject()
                .write("date", sdf.format(billableTime.getDate()))
                .write("minutes", billableTime.getMinutes())
                .write("employee", "/employees/" + billableTime.getEmployee().getId())
                .write("project", "/projects/" + billableTime.getProject().getId());
        if (billableTime.getId() != null) {
            jg.write("id", billableTime.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }
}

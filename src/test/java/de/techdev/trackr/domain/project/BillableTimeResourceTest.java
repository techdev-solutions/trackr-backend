package de.techdev.trackr.domain.project;

import de.techdev.trackr.core.web.MockMvcTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class BillableTimeResourceTest extends MockMvcTest {

    @Autowired
    private BillableTimeDataOnDemand billableTimeDataOnDemand;

    @Before
    public void setUp() throws Exception {
        billableTimeDataOnDemand.init();
    }

    @Test
    public void rootNotExported() throws Exception {
        mockMvc.perform(
                get("/billableTimes")
                        .session(employeeSession()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void oneAllowedForSupervisor() throws Exception {
        BillableTime billableTime = billableTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/billableTimes/" + billableTime.getId())
                        .session(supervisorSession()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(billableTime.getId().intValue())));
    }

    @Test
    public void oneForbiddenForEmployee() throws Exception {
        BillableTime billableTime = billableTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/billableTimes/" + billableTime.getId())
                        .session(employeeSession()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void createAllowedForSupervisor() throws Exception {
        BillableTime billableTime = billableTimeDataOnDemand.getNewTransientObject(500);
        mockMvc.perform(
                post("/billableTimes")
                        .session(supervisorSession())
                        .content(createBillableTimeJson(billableTime)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", isNotNull()));
    }

    @Test
    public void createForbiddenForEmployee() throws Exception {
        BillableTime billableTime = billableTimeDataOnDemand.getNewTransientObject(500);
        mockMvc.perform(
                post("/billableTimes")
                        .session(employeeSession(billableTime.getEmployee().getId()))
                        .content(createBillableTimeJson(billableTime)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteAllowedForSupervisor() throws Exception {
        BillableTime billableTime = billableTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/billableTimes/" + billableTime.getId())
                        .session(supervisorSession()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteForbiddenForEmployee() throws Exception {
        BillableTime billableTime = billableTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/billableTimes/" + billableTime.getId())
                        .session(employeeSession()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateAllowedForSupervisor() throws Exception {
        BillableTime billableTime = billableTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/billableTimes/" + billableTime.getId())
                        .session(supervisorSession())
                        .content(createBillableTimeJson(billableTime)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(billableTime.getId().intValue())));
    }

    /**
     * TODO: this fails with HTTP 400 because findOne is annotated with @PreAuthorize and spring-data-rest doesn't forward the AccessDeniedException correctly
     */
    @Test
    @Ignore
    public void updateForbiddenForEmployee() throws Exception {
        BillableTime billableTime = billableTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/billableTimes/" + billableTime.getId())
                        .session(employeeSession(billableTime.getEmployee().getId()))
                        .content(createBillableTimeJson(billableTime)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteEmployeeForbidden() throws Exception {
        BillableTime billableTime = billableTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/billableTimes/" + billableTime.getId() + "/employee")
                        .session(adminSession()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteProjectForbidden() throws Exception {
        BillableTime billableTime = billableTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/billableTimes/" + billableTime.getId() + "/project")
                        .session(adminSession()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateEmployeeAllowedForSupervisor() throws Exception {
        BillableTime billableTime = billableTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/billableTimes/" + billableTime.getId() + "/employee")
                        .session(supervisorSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/employees/" + billableTime.getEmployee().getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateProjectAllowedForSupervisor() throws Exception {
        BillableTime billableTime = billableTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/billableTimes/" + billableTime.getId() + "/employee")
                        .session(supervisorSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/employees/" + billableTime.getProject().getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateEmployeeForbiddenForEmployee() throws Exception {
        BillableTime billableTime = billableTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/billableTimes/" + billableTime.getId() + "/employee")
                        .session(employeeSession(billableTime.getEmployee().getId()))
                        .header("Content-Type", "text/uri-list")
                        .content("/employees/" + billableTime.getEmployee().getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateProjectForbiddenForEmployee() throws Exception {
        BillableTime billableTime = billableTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/billableTimes/" + billableTime.getId() + "/employee")
                        .session(employeeSession(billableTime.getEmployee().getId()))
                        .header("Content-Type", "text/uri-list")
                        .content("/employees/" + billableTime.getProject().getId()))
                .andExpect(status().isForbidden());
    }

    private String createBillableTimeJson(BillableTime billableTime) throws Exception {
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

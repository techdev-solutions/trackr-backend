package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.core.web.MockMvcTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class TravelExpenseReportResourceTest extends MockMvcTest {

    @Autowired
    private TravelExpenseReportDataOnDemand travelExpenseReportDataOnDemand;

    @Autowired
    private TravelExpenseReportRepository travelExpenseReportRepository;

    @Autowired
    private TravelExpenseDataOnDemand travelExpenseDataOnDemand;

    @Before
    public void setUp() throws Exception {
        travelExpenseReportDataOnDemand.init();
    }

    @Test
    public void rootNotExported() throws Exception {
        mockMvc.perform(
                get("/travelExpenseReports")
                        .session(adminSession())
        ).andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void oneNotAllowedForOther() throws Exception {
        TravelExpenseReport travelExpenseReport = travelExpenseReportDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/travelExpenseReports/" + travelExpenseReport.getId())
                        .session(employeeSession(travelExpenseReport.getEmployee().getId() + 1))
        )
               .andExpect(status().isForbidden());
    }

    @Test
    public void oneAllowedForSelf() throws Exception {
        TravelExpenseReport travelExpenseReport = travelExpenseReportDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/travelExpenseReports/" + travelExpenseReport.getId())
                        .session(employeeSession(travelExpenseReport.getEmployee().getId()))
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("id", isNotNull()));
    }

    @Test
    public void createAllowed() throws Exception {
        TravelExpenseReport travelExpenseReport = travelExpenseReportDataOnDemand.getNewTransientObject(500);
        mockMvc.perform(
                post("/travelExpenseReports/")
                        .session(employeeSession(travelExpenseReport.getEmployee().getId()))
                        .content(createTravelExpneseReportJson(travelExpenseReport))
        )
               .andExpect(status().isCreated())
               .andExpect(jsonPath("id", isNotNull()));
    }

    @Test
    public void updateAllowedForSelf() throws Exception {
        TravelExpenseReport travelExpenseReport = travelExpenseReportDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/travelExpenseReports/" + travelExpenseReport.getId())
                        .session(employeeSession(travelExpenseReport.getEmployee().getId()))
                        .content(createTravelExpneseReportJson(travelExpenseReport))
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("id", isNotNull()));
    }

    @Test
    @Ignore
    public void updateForbiddenForOther() throws Exception {
        TravelExpenseReport travelExpenseReport = travelExpenseReportDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/travelExpenseReports/" + travelExpenseReport.getId())
                        .session(employeeSession(travelExpenseReport.getEmployee().getId() + 1))
                        .content(createTravelExpneseReportJson(travelExpenseReport))
        )
               .andExpect(status().isForbidden());
    }

    @Test
    public void oneAllowedForSupervisor() throws Exception {
        TravelExpenseReport travelExpenseReport = travelExpenseReportDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/travelExpenseReports/" + travelExpenseReport.getId())
                        .session(supervisorSession())
        )
               .andExpect(status().isOk())
               .andExpect(jsonPath("id", isNotNull()));
    }

    @Test
    public void updateEmployeeNotAllowedForSupervisor() throws Exception {
        TravelExpenseReport travelExpenseReport = travelExpenseReportDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/travelExpenseReports/" + travelExpenseReport.getId() + "/employee")
                        .session(supervisorSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/employees/" + travelExpenseReport.getEmployee().getId()))
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteEmployeeNotAllowed() throws Exception {
        TravelExpenseReport travelExpenseReport = travelExpenseReportDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/travelExpenseReports/" + travelExpenseReport.getId() + "/employee")
                        .session(supervisorSession()))
               .andExpect(status().isForbidden());
    }

    @Test
    public void addTravelExpenseAllowedForSelf() throws Exception {
        TravelExpenseReport travelExpenseReport = travelExpenseReportDataOnDemand.getRandomObject();
        TravelExpense travelExpense = travelExpenseDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/travelExpenseReports/" + travelExpenseReport.getId() + "/expenses")
                        .session(employeeSession(travelExpenseReport.getEmployee().getId()))
                        .header("Content-Type", "text/uri-list")
                        .content("/travelExpenses/" + travelExpense.getId()))
               .andExpect(status().isNoContent());
    }

    @Test
    public void addTravelExpenseNotAllowedForOther() throws Exception {
        TravelExpenseReport travelExpenseReport = travelExpenseReportDataOnDemand.getRandomObject();
        TravelExpense travelExpense = travelExpenseDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/travelExpenseReports/" + travelExpenseReport.getId() + "/expenses")
                        .session(employeeSession(travelExpenseReport.getEmployee().getId() + 1))
                        .header("Content-Type", "text/uri-list")
                        .content("/travelExpenses/" + travelExpense.getId()))
               .andExpect(status().isForbidden());
    }

    private String createTravelExpneseReportJson(TravelExpenseReport travelExpenseReport) throws Exception {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
          .write("status", travelExpenseReport.getStatus().toString())
          .write("employee", "/employees/" + travelExpenseReport.getEmployee().getId());
        if (travelExpenseReport.getId() != null) {
            jg.write("id", travelExpenseReport.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }
}

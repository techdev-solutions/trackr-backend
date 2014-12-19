package de.techdev.trackr.domain.employee.expenses.report;

import de.techdev.trackr.core.security.AuthorityMocks;
import de.techdev.trackr.domain.AbstractDomainResourceTest;
import de.techdev.trackr.domain.employee.expenses.reports.Report;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.function.Function;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class ReportResourceTest extends AbstractDomainResourceTest<Report> {

    private final Function<Report, MockHttpSession> sameEmployeeSessionProvider;
    private final Function<Report, MockHttpSession> otherEmployeeSessionProvider;

    public ReportResourceTest() {
        this.sameEmployeeSessionProvider = travelExpenseReport -> employeeSession(travelExpenseReport.getEmployee().getId());
        this.otherEmployeeSessionProvider = travelExpenseReport -> employeeSession(travelExpenseReport.getEmployee().getId() + 1);
    }

    @Override
    protected String getResourceName() {
        return "travelExpenseReports";
    }

    @Test
    public void rootNotExported() throws Exception {
        assertThat(root(adminSession()), isMethodNotAllowed());
    }

    @Test
    public void oneNotAllowedForOther() throws Exception {
        assertThat(one(otherEmployeeSessionProvider), isForbidden());
    }

    @Test
    public void oneAllowedForSelf() throws Exception {
        assertThat(one(sameEmployeeSessionProvider), isAccessible());
    }

    @Test
    public void createAllowed() throws Exception {
        assertThat(create(sameEmployeeSessionProvider), isCreated());
    }

    @Test
    public void updateAllowedForSelf() throws Exception {
        assertThat(update(sameEmployeeSessionProvider), isForbidden());
    }

    @Test
    @Ignore
    public void updateForbiddenForOther() throws Exception {
        assertThat(update(otherEmployeeSessionProvider), isForbidden());
    }

    @Test
    public void oneAllowedForSupervisor() throws Exception {
        assertThat(one(supervisorSession()), isAccessible());
    }

    @Test
    public void travelExpensesAllowedForSelf() throws Exception {
        Report travelExpenseReport = dataOnDemand.getRandomObject();
        assertThat(oneUrl(employeeSession(travelExpenseReport.getEmployee().getId()), "/travelExpenseReports/" + travelExpenseReport.getId() + "/expenses"), isAccessible());
    }

    @Test
    public void updateEmployeeNotAllowedForSupervisor() throws Exception {
        assertThat(updateLink(supervisorSession(), "employee", "/employees/0"), isForbidden());
    }

    @Test
    public void deleteEmployeeNotAllowed() throws Exception {
        Report travelExpenseReport = dataOnDemand.getRandomObject();
        assertThat(removeUrl(supervisorSession(), "/travelExpenseReports/" + travelExpenseReport.getId() + "/employee"), isForbidden());
    }

    @Test
    public void addTravelExpenseAllowedForSelf() throws Exception {
        assertThat(updateLink(sameEmployeeSessionProvider, "expenses", "/travelExpenses/0"), isNoContent());
    }

    @Test
    public void addTravelExpenseNotAllowedForOther() throws Exception {
        assertThat(updateLink(otherEmployeeSessionProvider, "expenses", "/travelExpenses/0"), isForbidden());
    }

    @Test
    public void deleteAllowedForOwnerIfPending() throws Exception {
        Report travelExpenseReport = dataOnDemand.getRandomObject();
        travelExpenseReport.setStatus(Report.Status.PENDING);
        repository.save(travelExpenseReport);
        assertThat(removeUrl(employeeSession(travelExpenseReport.getEmployee().getId()), "/travelExpenseReports/" + travelExpenseReport.getId()), isNoContent());
    }

    @Test
    public void deleteAllowedForAdmin() throws Exception {
        assertThat(remove(adminSession()), isNoContent());
    }

    @Test
    public void deleteForbiddenForOtherEvenIfPending() throws Exception {
        Report travelExpenseReport = dataOnDemand.getRandomObject();
        travelExpenseReport.setStatus(Report.Status.PENDING);
        repository.save(travelExpenseReport);
        assertThat(removeUrl(employeeSession(travelExpenseReport.getEmployee().getId() + 1), "/travelExpenseReports/" + travelExpenseReport.getId()), isForbidden());
    }

    @Test
    public void deleteForbiddenForOwnerIfSubmitted() throws Exception {
        Report travelExpenseReport = dataOnDemand.getRandomObject();
        travelExpenseReport.setStatus(Report.Status.SUBMITTED);
        repository.save(travelExpenseReport);
        assertThat(removeUrl(employeeSession(travelExpenseReport.getEmployee().getId()), "/travelExpenseReports/" + travelExpenseReport.getId()), isForbidden());
    }

    @Test
    public void submitNotAllowedForOtherSupervisor() throws Exception {
        Report travelExpenseReport = dataOnDemand.getRandomObject();
        travelExpenseReport.setStatus(Report.Status.PENDING);
        repository.save(travelExpenseReport);
        mockMvc.perform(
                put("/travelExpenseReports/" + travelExpenseReport.getId() + "/submit")
                        .session(supervisorSession(travelExpenseReport.getEmployee().getId() + 1))
        )
                .andExpect(status().isForbidden());

        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.adminAuthentication());
        Report one = repository.findOne(travelExpenseReport.getId());
        assertThat(one.getStatus(), is(Report.Status.PENDING));
    }

    @Test
    public void approveNotAllowedForOwningSupervisor() throws Exception {
        Report travelExpenseReport = dataOnDemand.getRandomObject();
        travelExpenseReport.setStatus(Report.Status.SUBMITTED);
        repository.save(travelExpenseReport);
        mockMvc.perform(
                put("/travelExpenseReports/" + travelExpenseReport.getId() + "/approve")
                        .session(supervisorSession(travelExpenseReport.getEmployee().getId()))
        )
                .andExpect(status().isForbidden());

        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.adminAuthentication());
        Report one = repository.findOne(travelExpenseReport.getId());
        assertThat(one.getStatus(), is(Report.Status.SUBMITTED));
    }

    @Test
    public void approveAllowedForSupervisor() throws Exception {
        Report travelExpenseReport = dataOnDemand.getRandomObject();
        travelExpenseReport.setStatus(Report.Status.SUBMITTED);
        repository.save(travelExpenseReport);
        mockMvc.perform(
                put("/travelExpenseReports/" + travelExpenseReport.getId() + "/approve")
                        .session(supervisorSession(travelExpenseReport.getEmployee().getId() + 1))
        )
                .andExpect(status().isNoContent());

        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.adminAuthentication());
        Report one = repository.findOne(travelExpenseReport.getId());
        assertThat(one.getStatus(), is(Report.Status.APPROVED));

    }

    @Test
    public void rejectAllowedForSupervisor() throws Exception {
        Report travelExpenseReport = dataOnDemand.getRandomObject();
        travelExpenseReport.setStatus(Report.Status.SUBMITTED);
        repository.save(travelExpenseReport);
        mockMvc.perform(
                put("/travelExpenseReports/" + travelExpenseReport.getId() + "/reject")
                        .session(supervisorSession(travelExpenseReport.getEmployee().getId() + 1))
        )
                .andExpect(status().isNoContent());

        SecurityContextHolder.getContext().setAuthentication(AuthorityMocks.adminAuthentication());
        Report one = repository.findOne(travelExpenseReport.getId());
        assertThat(one.getStatus(), is(Report.Status.REJECTED));

    }

    @Test
    public void pdfExport() throws Exception {
        Report report = dataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/travelExpenseReports/" + report.getId() + "/pdf")
                        .session(supervisorSession())
        )
                .andExpect(status().isOk());
    }

    @Test
    public void pdfExportAsEmployee() throws Exception {
        Report report = dataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/travelExpenseReports/" + report.getId() + "/pdf")
                        .session(employeeSession(report.getEmployee().getId()))
        )
                .andExpect(status().isOk());
    }

    @Override
    protected String getJsonRepresentation(Report travelExpenseReport) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        jg.writeStartObject()
          .write("status", travelExpenseReport.getStatus().toString())
          .write("employee", "/employees/" + travelExpenseReport.getEmployee().getId())
          .write("debitor", "/companies/" + travelExpenseReport.getDebitor().getId());

        if(travelExpenseReport.getSubmissionDate() != null) {
            jg.write("submissionDate", sdf.format(travelExpenseReport.getSubmissionDate()));
        }
        if (travelExpenseReport.getProject() != null) {
            jg.write("project", "/projects/" + travelExpenseReport.getProject().getId());
        }
        if (travelExpenseReport.getId() != null) {
            jg.write("id", travelExpenseReport.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }
}

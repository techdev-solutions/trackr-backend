package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.AbstractDomainResourceTest;
import de.techdev.trackr.domain.employee.expenses.reports.Report;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import javax.json.stream.JsonGenerator;

import java.io.StringWriter;
import java.util.function.Function;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.*;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
/**
 * @author Moritz Schulze
 */
public class TravelExpenseResourceTest extends AbstractDomainResourceTest<TravelExpense> {

    private final Function<TravelExpense, MockHttpSession> sameEmployeeSessionProvider;
    private final Function<TravelExpense, MockHttpSession> otherEmployeeSessionProvider;

    public TravelExpenseResourceTest() {
        sameEmployeeSessionProvider = travelExpense -> employeeSession(travelExpense.getReport().getEmployee().getId());
        otherEmployeeSessionProvider = travelExpense -> employeeSession(travelExpense.getReport().getEmployee().getId() + 1);
    }

    @Override
    protected String getResourceName() {
        return "travelExpenses";
    }

    @Test
    public void rootNotExported() throws Exception {
        assertThat(root(adminSession()), isMethodNotAllowed());
    }

    @Test
    public void oneNotExported() throws Exception {
        assertThat(one(adminSession()), isMethodNotAllowed());
    }

    @Test
    public void createAllowedForSelf() throws Exception {
        TravelExpense travelExpense = dataOnDemand.getNewTransientObject(500);
        travelExpense.getReport().setStatus(Report.Status.PENDING);
        repository.save(travelExpense);
        mockMvc.perform(
                post("/travelExpenses/")
                        .session(sameEmployeeSessionProvider.apply(travelExpense))
                        .content(getJsonRepresentation(travelExpense))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", isNotNull()));
    }

    @Test
    @Ignore
    public void createNotAllowedForOther() throws Exception {
        assertThat(create(otherEmployeeSessionProvider), isForbidden());
    }

    @Test
    public void updateAllowedForSelf() throws Exception {
        TravelExpense travelExpense = dataOnDemand.getRandomObject();
        travelExpense.getReport().setStatus(Report.Status.PENDING);
        repository.save(travelExpense);
        mockMvc.perform(
                put("/travelExpenses/" + travelExpense.getId())
                        .session(sameEmployeeSessionProvider.apply(travelExpense))
                        .content(getJsonRepresentation(travelExpense))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", isNotNull()));
    }

    @Test
    public void deletePendingAllowed() throws Exception {
        TravelExpense travelExpense = dataOnDemand.getRandomObject();
        travelExpense.getReport().setStatus(Report.Status.PENDING);
        repository.save(travelExpense);
        assertThat(removeUrl(employeeSession(travelExpense.getReport().getEmployee().getId()), "/travelExpenses/" + travelExpense.getId()), isNoContent());
    }

    @Test
    public void deleteAcceptedNotAllowed() throws Exception {
        TravelExpense travelExpense = dataOnDemand.getRandomObject();
        travelExpense.getReport().setStatus(Report.Status.APPROVED);
        repository.save(travelExpense);
        assertThat(removeUrl(employeeSession(travelExpense.getReport().getEmployee().getId()), "/travelExpenses/" + travelExpense.getId()), isForbidden());
    }

    @Test
    public void deleteSubmittedNotAllowed() throws Exception {
        TravelExpense travelExpense = dataOnDemand.getRandomObject();
        travelExpense.getReport().setStatus(Report.Status.SUBMITTED);
        repository.save(travelExpense);
        assertThat(removeUrl(employeeSession(travelExpense.getReport().getEmployee().getId()), "/travelExpenses/" + travelExpense.getId()), isForbidden());
    }

    @Test
    @Ignore
    public void changeReportNotAllowed() throws Exception {
        assertThat(updateLink(supervisorSession(), "report", "/travelExpenseReports/0"), isForbidden());
    }

    @Test
    public void accessTypes() throws Exception {
        mockMvc.perform(
                get("/travelExpenses/types")
                    .session(employeeSession())
        )
                .andExpect(status().isOk());
    }

    @Override
    protected String getJsonRepresentation(TravelExpense travelExpense) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
          .write("cost", travelExpense.getCost())
          .write("vat", travelExpense.getVat())
          .write("fromDate", ISO_LOCAL_DATE.format(travelExpense.getFromDate()))
          .write("toDate", ISO_LOCAL_DATE.format(travelExpense.getToDate()))
          .write("submissionDate", ISO_INSTANT.format(travelExpense.getSubmissionDate()))
          .write("type", travelExpense.getType().toString())
          .write("report", "/travelExpenseReports/" + travelExpense.getReport().getId());

        if(travelExpense.getComment() != null) {
            jg.write("comment", travelExpense.getComment());
        }

        if (travelExpense.getId() != null) {
            jg.write("id", travelExpense.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }
}

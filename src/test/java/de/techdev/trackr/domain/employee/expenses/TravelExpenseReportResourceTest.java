package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.domain.AbstractDomainResourceTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;
import java.util.function.Function;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class TravelExpenseReportResourceTest extends AbstractDomainResourceTest<TravelExpenseReport> {

    private final Function<TravelExpenseReport, MockHttpSession> sameEmployeeSessionProvider;
    private final Function<TravelExpenseReport, MockHttpSession> otherEmployeeSessionProvider;

    public TravelExpenseReportResourceTest() {
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
        assertThat(update(sameEmployeeSessionProvider), isUpdated());
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
        TravelExpenseReport travelExpenseReport = dataOnDemand.getRandomObject();
        assertThat(oneUrl(employeeSession(travelExpenseReport.getEmployee().getId()), "/travelExpenseReports/" + travelExpenseReport.getId() + "/expenses"), isAccessible());
    }

    @Test
    public void updateEmployeeNotAllowedForSupervisor() throws Exception {
        assertThat(updateLink(supervisorSession(), "employee", "/employees/0"), isForbidden());
    }

    @Test
    public void deleteEmployeeNotAllowed() throws Exception {
        TravelExpenseReport travelExpenseReport = dataOnDemand.getRandomObject();
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

    @Override
    protected String getJsonRepresentation(TravelExpenseReport travelExpenseReport) {
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

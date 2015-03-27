package de.techdev.trackr.domain.employee.expenses.report;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static de.techdev.test.rest.DomainResourceTestMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Sql("resourceTest.sql")
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@OAuthRequest
public class ReportResourceSecurityTest extends AbstractDomainResourceSecurityTest {

    private ReportJsonGenerator jsonGenerator = new ReportJsonGenerator();

    @Override
    protected String getResourceName() {
        return "travelExpenseReports";
    }

    @Test
    @OAuthRequest("ROLE_ADMIN")
    public void rootNotExported() throws Exception {
        assertThat(root(), isMethodNotAllowed());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void oneAllowedForSupervisor() throws Exception {
        assertThat(one(0L), isAccessible());
    }

    @Test
    @OAuthRequest(username = "someone.else@techdev.de")
    public void oneNotAllowedForOther() throws Exception {
        assertThat(one(0L), isForbidden());
    }

    @Test
    public void oneAllowedForSelf() throws Exception {
        assertThat(one(0L), isAccessible());
    }

    @Test
    public void createAllowed() throws Exception {
        String json = jsonGenerator.start().withDebitorId(0L).withEmployeeId(0L).build();
        assertThat(create(json), isCreated());
    }

    @Test
    public void updateNotAllowedForSelf() throws Exception {
        String json = jsonGenerator.start().withDebitorId(0L).withEmployeeId(0L).apply(r -> r.setId(0L)).build();
        assertThat(update(0L, json), isForbidden());
    }

    @Test
    @Ignore("yields 400 instead of 403 ?")
    @OAuthRequest(username = "someone.else@techdev.de")
    public void updateForbiddenForOther() throws Exception {
        String json = jsonGenerator.start().withDebitorId(0L).withEmployeeId(0L).apply(r -> r.setId(0L)).build();
        assertThat(update(0L, json), isForbidden());
    }

    @Test
    public void deleteAllowedForOwnerIfPending() throws Exception {
        assertThat(remove(0L), isNoContent());
    }

    @Test
    public void deleteForbiddenForOwnerIfSubmitted() throws Exception {
        assertThat(remove(1L), isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_ADMIN")
    public void deleteAllowedForAdmin() throws Exception {
        assertThat(remove(0L), isNoContent());
    }

    @Test
    @OAuthRequest(username = "someone.else@techdev.de")
    public void deleteForbiddenForOtherEvenIfPending() throws Exception {
        assertThat(remove(0L), isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void pdfExport() throws Exception {
        ResponseEntity<byte[]> response = restTemplate.getForEntity(host + "/travelExpenseReports/0/pdf", byte[].class);
        assertThat(response, isAccessible());
    }

    @Test
    public void pdfExportAsEmployee() throws Exception {
        ResponseEntity<byte[]> response = restTemplate.getForEntity(host + "/travelExpenseReports/0/pdf", byte[].class);
        assertThat(response, isAccessible());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void updateEmployeeNotAllowedForSupervisor() throws Exception {
        assertThat(updateLink(0L, "employee", "/employees/0"), isForbidden());
    }

    @Test
    public void addTravelExpenseAllowedForSelf() throws Exception {
        assertThat(updateLink(0L, "expenses", "/travelExpenses/0"), isNoContent());
    }

    @Test
    @OAuthRequest(username = "someone.else@techdev.de")
    public void addTravelExpenseNotAllowedForOther() throws Exception {
        assertThat(updateLink(0L, "expenses", "/travelExpenses/0"), isForbidden());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR", username = "someone.else@techdev.de")
    public void submitNotAllowedForOtherSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(host + "/travelExpenseReports/0/submit", HttpMethod.PUT, HttpEntity.EMPTY, String.class);
        assertThat(response, isForbidden());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR")
    public void approveNotAllowedForOwningSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(host + "/travelExpenseReports/0/approve", HttpMethod.PUT, HttpEntity.EMPTY, String.class);
        assertThat(response, isForbidden());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR", username = "someone.else@techdev.de")
    public void approveAllowedForSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(host + "/travelExpenseReports/0/approve", HttpMethod.PUT, HttpEntity.EMPTY, String.class);
        assertThat(response, isNoContent());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR", username = "someone.else@techdev.de")
    public void rejectAllowedForSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(host + "/travelExpenseReports/0/reject", HttpMethod.PUT, HttpEntity.EMPTY, String.class);
        assertThat(response, isNoContent());
    }

}

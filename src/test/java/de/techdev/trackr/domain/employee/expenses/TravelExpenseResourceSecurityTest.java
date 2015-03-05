package de.techdev.trackr.domain.employee.expenses;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static de.techdev.test.rest.DomainResourceTestMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Sql("resourceTest.sql")
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@OAuthRequest
public class TravelExpenseResourceSecurityTest extends AbstractDomainResourceSecurityTest {

    private TravelExpenseJsonGenerator jsonGenerator = new TravelExpenseJsonGenerator();

    @Override
    protected String getResourceName() {
        return "travelExpenses";
    }

    @Test
    @OAuthRequest("ROLE_ADMIN")
    public void rootNotExported() throws Exception {
        assertThat(root(), isMethodNotAllowed());
    }

    @Test
    @OAuthRequest("ROLE_ADMIN")
    public void oneNotExported() throws Exception {
        assertThat(one(0L), isMethodNotAllowed());
    }

    @Test
    public void createAllowedForSelf() throws Exception {
        String json = jsonGenerator.start().withReportId(0L).build();
        assertThat(create(json), isCreated());
    }

    @Test
    @Ignore
    @OAuthRequest(username = "someone.else@techdev.de")
    public void createNotAllowedForOther() throws Exception {
        String json = jsonGenerator.start().withReportId(0L).build();
        assertThat(create(json), isForbidden());
    }

    @Test
    public void updateAllowedForSelf() throws Exception {
        String json = jsonGenerator.start().withReportId(0L).apply(t -> t.setId(0L)).build();
        assertThat(update(0L, json), isUpdated());
    }

    @Test
    public void deletePendingAllowed() throws Exception {
        assertThat(remove(0L), isNoContent());
    }

    @Test
    public void deleteAcceptedNotAllowed() throws Exception {
        assertThat(remove(1L), isForbidden());
    }

    @Test
    public void deleteSubmittedNotAllowed() throws Exception {
        assertThat(remove(2L), isForbidden());
    }

    @Test
    @Ignore
    public void changeReportNotAllowed() throws Exception {
        assertThat(updateLink(0L, "report", "/travelExpenseReports/0"), isForbidden());
    }

    @Test
    public void accessTypes() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/travelExpenses/types", String.class);
        assertThat(response, isAccessible());
    }

}

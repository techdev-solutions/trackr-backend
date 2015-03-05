package de.techdev.trackr.domain.employee.expenses.report.comment;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import static de.techdev.test.rest.DomainResourceTestMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Sql("resourceTest.sql")
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@OAuthRequest
public class CommentResourceSecurityTest extends AbstractDomainResourceSecurityTest {

    private CommentJsonGenerator jsonGenerator = new CommentJsonGenerator();

    @Override
    protected String getResourceName() {
        return "travelExpenseReportComments";
    }

    @Test
    public void rootNotExported() throws Exception {
        assertThat(root(), isMethodNotAllowed());
    }

    @Test
    public void oneNotExported() throws Exception {
        assertThat(one(0L), isMethodNotAllowed());
    }

    @Test
    public void createAllowedForOwningEmployee() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).withReportId(0L).build();
        assertThat(create(json), isCreated());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR", username = "supervisor@techdev.de")
    public void createAllowedForSupervisor() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).withReportId(0L).build();
        assertThat(create(json), isCreated());
    }

    @Test
    @OAuthRequest(value = "ROLE_ADMIN")
    public void updateForbidden() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).withReportId(0L).apply(c -> c.setId(0L)).build();
        assertThat(update(0L, json), isForbidden());
    }

    @Test
    @OAuthRequest(value = "ROLE_ADMIN")
    public void deleteNotExported() throws Exception {
        assertThat(remove(0L), isMethodNotAllowed());
    }
}
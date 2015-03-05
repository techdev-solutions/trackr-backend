package de.techdev.trackr.domain.employee.vacation;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import static de.techdev.test.rest.DomainResourceTestMatchers.isAccessible;
import static de.techdev.test.rest.DomainResourceTestMatchers.isMethodNotAllowed;
import static org.junit.Assert.assertThat;

@Sql("holiday/resourceTest.sql")
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@OAuthRequest
public class HolidayResourceTest extends AbstractDomainResourceSecurityTest {

    @Override
    protected String getResourceName() {
        return "holidays";
    }

    @Test
    public void rootAccessible() throws Exception {
        assertThat(root(), isAccessible());
    }

    @Test
    public void oneAccessible() throws Exception {
        assertThat(one(0L), isAccessible());
    }

    @Test
    public void createNotExported() throws Exception {
        assertThat(create("{}"), isMethodNotAllowed());
    }

    @Test
    public void updateNotExported() throws Exception {
        assertThat(update(0L, "{}"), isMethodNotAllowed());
    }

    @Test
    public void deleteNotExported() throws Exception {
        assertThat(remove(0L), isMethodNotAllowed());
    }

}
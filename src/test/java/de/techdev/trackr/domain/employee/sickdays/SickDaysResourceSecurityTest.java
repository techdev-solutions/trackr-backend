package de.techdev.trackr.domain.employee.sickdays;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import static de.techdev.test.rest.DomainResourceTestMatchers.*;
import static org.echocat.jomon.testing.Assert.assertThat;

@Sql("resourceTest.sql")
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@OAuthRequest
public class SickDaysResourceSecurityTest extends AbstractDomainResourceSecurityTest {

    private SickDaysJsonGenerator jsonGenerator = new SickDaysJsonGenerator();

    @Override
    protected String getResourceName() {
        return "sickDays";
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void rootIsNotAccessibleForAdmin() throws Exception {
        assertThat(root(), isMethodNotAllowed());
    }

    @Test
    public void oneIsAllowedForEmployee() throws Exception {
        assertThat(one(0L), isAccessible());
    }

    @Test
    @OAuthRequest(username = "someone.else@techdev.de")
    public void oneIsForbiddenForOther() throws Exception {
        assertThat(one(0L), isForbidden());
    }

    @Test
    public void createIsAllowedForEmployee() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).build();
        assertThat(create(json), isCreated());
    }

    @Test
    @OAuthRequest(username = "someone.else@techdev.de")
    @Ignore
//    Not testable at the moment since it returns 400 instead of 403 (it can't convert the JSON because the employee is also restricted).
    public void createIsForbiddenForOther() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).build();
        assertThat(create(json), isCreated());
    }

    @Test
    @OAuthRequest("ROLE_ADMIN")
    public void deleteIsAllowedForAdmin() throws Exception {
        assertThat(remove(0L), isNoContent());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void deleteIsForbiddenForSupervisor() throws Exception {
        assertThat(remove(0L), isForbidden());
    }

    @Test
    public void updateIsAllowedForEmployee() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).apply(s -> s.setId(0L)).build();
        assertThat(update(0L, json), isUpdated());
    }

    @Test
    @Ignore
    @OAuthRequest(username = "someone.else@techdev.de")
    // Not testable at the moment since it returns 400 instead of 403 (it can't convert the JSON because the employee is also restricted).
    public void updateIsForbiddenForOther() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).apply(s -> s.setId(0L)).build();
        assertThat(update(0L, json), isForbidden());
    }

    @Test
    public void findByEmployeeIsAllowedForEmployee() throws Exception {
        assertThat(oneUrl("/sickDays/search/findByEmployee?employee=0"), isAccessible());
    }

    @Test
    @OAuthRequest(username = "someone.else@techdev.de")
    @Ignore
    // Not testable at the moment since it returns 400 instead of 403 (it can't convert the JSON because the employee is also restricted).
    public void findByEmployeeIsForbiddenForOther() throws Exception {
        assertThat(oneUrl("/sickDays/search/findByEmployee?employee=0"), isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_ADMIN")
    public void findByStartDateBetweenOrEndDateBetweenIsAllowedForAdmin() throws Exception {
        assertThat(
                oneUrl("/sickDays/search/findByStartDateBetweenOrEndDateBetween?startLower=2014-07-01&startHigher=2014-07-31&endLower=2014-07-08&endHigher=2014-08-09"), isAccessible()
        );
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void findByStartDateBetweenOrEndDateBetweenIsForbiddenForSupervisor() throws Exception {
        assertThat(
                oneUrl("/sickDays/search/findByStartDateBetweenOrEndDateBetween?startLower=2014-07-01&startHigher=2014-07-31&endLower=2014-07-08&endHigher=2014-08-09"), isForbidden()
        );
    }
}

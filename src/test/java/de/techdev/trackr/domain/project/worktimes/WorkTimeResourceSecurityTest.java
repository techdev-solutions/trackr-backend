package de.techdev.trackr.domain.project.worktimes;

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
public class WorkTimeResourceSecurityTest extends AbstractDomainResourceSecurityTest {

    private WorkTimeJsonGenerator jsonGenerator = new WorkTimeJsonGenerator();

    @Override
    protected String getResourceName() {
        return "workTimes";
    }

    @Test
    public void rootNotExported() throws Exception {
        assertThat(root(), isMethodNotAllowed());
    }

    @Test
    public void oneAllowedForOwner() throws Exception {
        assertThat(one(0L), isAccessible());
    }

    @Test
    @OAuthRequest(username = "someone.else@techdev.de")
    public void oneForbiddenForOther() throws Exception {
        assertThat(one(0L), isForbidden());
    }

    @Test
    public void createAllowedForEveryoneIfIsEmployee() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).withProjectId(0L).build();
        assertThat(create(json), isCreated());
    }

    @Test
    public void updateAllowedForOwner() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).withProjectId(0L).apply(w -> w.setId(0L)).build();
        assertThat(update(0L, json), isUpdated());
    }

    @Test
    @OAuthRequest(value = "ROLE_ADMIN", username = "admin@techdev.de")
    public void updateAllowedForAdmin() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).withProjectId(0L).apply(w -> w.setId(0L)).build();
        assertThat(update(0L, json), isUpdated());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR", username = "supervisor@techdev.de")
    public void updateNotAllowedForSupervisor() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).withProjectId(0L).apply(w -> w.setId(0L)).build();
        assertThat(update(0L, json), isForbidden());
    }

    @Test
    public void deleteAllowedForOwner() throws Exception {
        assertThat(remove(0L), isNoContent());
    }

    @Test
    @OAuthRequest(value = "ROLE_ADMIN", username = "admin@techdev.de")
    public void deleteAllowedForAdmin() throws Exception {
        assertThat(remove(0L), isNoContent());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR", username = "supervisor@techdev.de")
    public void deleteNotAllowedForSupervisor() throws Exception {
        assertThat(remove(0L), isForbidden());
    }

    /**
     * TODO: when trying to set to another employee the EmployeeRepository will deny accessing that employee which will result in a HTTP 400
     * TODO: When trying to set to the same employee our Exception thrown in the {@link de.techdev.trackr.domain.project.worktimes.WorkTimeEventHandler}
     * TODO: won't be propagated to Web MVC.
     * TODO: so in conclusion updating does not work but will produce a 500 or 400 instead of a 405.
     * @throws Exception
     */
    @Test
    @Ignore
    public void updateEmployeeNotAllowed() throws Exception {
        assertThat(updateLink(0L, "employee", "/employees/0"), isMethodNotAllowed());
    }

    @Test
    @OAuthRequest(value = "ROLE_ADMIN")
    public void deleteEmployeeNotAllowed() throws Exception {
        assertThat(removeUrl("/workTimes/0/employee"), isForbidden());
    }

    @Test
    @OAuthRequest(value = "ROLE_ADMIN")
    public void deleteProjectNotAllowed() throws Exception {
        assertThat(removeUrl("/workTimes/0/project"), isForbidden());
    }

    @Test
    public void updateProjectAllowedForOwner() throws Exception {
        assertThat(updateLink(0L, "project", "/projects/0"), isNoContent());
    }

    @Test
    @OAuthRequest(value = "ROLE_ADMIN", username = "admin@techdev.de")
    public void updateProjectAllowedForAdmin() throws Exception {
        assertThat(updateLink(0L, "project", "/projects/0"), isNoContent());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR", username = "supervisor@techdev.de")
    public void updateProjectForbiddenForSupervisor() throws Exception {
        assertThat(updateLink(0L, "project", "/projects/0"), isForbidden());
    }

    @Test
    public void findByEmployeeAndDateOrderByStartTimeAscAllowedForOwner() throws Exception {
        ResponseEntity<String> response = restTemplate
                .getForEntity(host + "/workTimes/search/findByEmployeeAndDateOrderByStartTimeAsc?employee=0&date=2014-01-01", String.class);
        assertThat(response, isAccessible());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR", username = "supervisor@techdev.de")
    public void findByEmployeeAndDateOrderByStartTimeAscAllowedForSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate
                .getForEntity(host + "/workTimes/search/findByEmployeeAndDateOrderByStartTimeAsc?employee=0&date=2014-01-01", String.class);
        assertThat(response, isAccessible());
    }

    @Test
    @OAuthRequest(value = "ROLE_ADMIN")
    public void findByDateBetweenAllowedForAdmin() throws Exception {
        ResponseEntity<String> response = restTemplate
                .getForEntity(host + "/workTimes/search/findByDateBetween?start=2014-01-01&end=2014-01-31", String.class);
        assertThat(response, isAccessible());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR", username = "supervisor@techdev.de")
    public void findByDateBetweenForbiddenForSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate
                .getForEntity(host + "/workTimes/search/findByDateBetween?start=2014-01-01&end=2014-01-31", String.class);
        assertThat(response, isForbidden());
    }

    @Test
    public void findByEmployeeAndDateBetweenOrderByDateAscStartTimeAscAllowedForOwner() throws Exception {
        ResponseEntity<String> response = restTemplate
                .getForEntity(host + "/workTimes/search/findByEmployeeAndDateBetweenOrderByDateAscStartTimeAsc?employee=0&start=2014-01-01&end=2014-01-31", String.class);
        assertThat(response, isAccessible());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR", username = "supervisor@techdev.de")
    public void findByEmployeeAndDateBetweenOrderByDateAscStartTimeAscAllowedForSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate
                .getForEntity(host + "/workTimes/search/findByEmployeeAndDateBetweenOrderByDateAscStartTimeAsc?employee=0&start=2014-01-01&end=2014-01-31", String.class);
        assertThat(response, isAccessible());
    }

    /**
     * This does not work because the accessDeniedException is thrown somewhere where spring-data-rest does not catch it.
     * So we get HTTP 400 instead of 403.
     * TODO: find out how to get a 403
     */
    @Test
    @Ignore
    @OAuthRequest(username = "someone.else@techdev.de")
    public void findByEmployeeAndDateBetweenOrderByDateAscStartTimeAscForbiddenForOther() throws Exception {
        ResponseEntity<String> response = restTemplate
                .getForEntity(host + "/workTimes/search/findByEmployeeAndDateBetweenOrderByDateAscStartTimeAsc?employee=0&start=2014-01-01&end=2014-01-31", String.class);
        assertThat(response, isForbidden());
    }

    /**
     * This does not work because the accessDeniedException is thrown somewhere where spring-data-rest does not catch it.
     * So we get HTTP 400 instead of 403.
     * TODO: find out how to get a 403
     */
    @Test
    @Ignore
    @OAuthRequest(username = "someone.else@techdev.de")
    public void findByEmployeeAndDateOrderByStartTimeAscForbiddenForOther() throws Exception {
        ResponseEntity<String> response = restTemplate
                .getForEntity(host + "/workTimes/search/findByEmployeeAndDateOrderByStartTimeAsc?employee=0&date=2014-01-01", String.class);
        assertThat(response, isForbidden());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR")
    public void findByProjectAndDateBetweenOrderByDateAscStartTimeAscAllowedForSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate
                .getForEntity(host + "/workTimes/search/findByProjectAndDateBetweenOrderByDateAscStartTimeAsc?project=0&start=2014-01-01&end=2014-01-31", String.class);
        assertThat(response, isAccessible());
    }

    @Test
    public void findByProjectAndDateBetweenOrderByDateAscStartTimeAscForbiddenForEmployee() throws Exception {
        ResponseEntity<String> response = restTemplate
                .getForEntity(host + "/workTimes/search/findByProjectAndDateBetweenOrderByDateAscStartTimeAsc?project=0&start=2014-01-01&end=2014-01-31", String.class);
        assertThat(response, isForbidden());
    }

}

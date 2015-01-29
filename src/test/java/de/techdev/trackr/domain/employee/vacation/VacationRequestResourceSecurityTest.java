package de.techdev.trackr.domain.employee.vacation;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static de.techdev.test.rest.DomainResourceTestMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Sql("resourceTest.sql")
@Sql("tableUuidMapping.sql")
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@OAuthRequest
public class VacationRequestResourceSecurityTest extends AbstractDomainResourceSecurityTest {

    private VacationRequestJsonGenerator jsonGenerator = new VacationRequestJsonGenerator();

    @Override
    protected String getResourceName() {
        return "vacationRequests";
    }

    @Test
    public void rootNotExported() throws Exception {
        assertThat(root(), isMethodNotAllowed());
    }

    @Test
    public void oneAllowedForEmployee() throws Exception {
        assertThat(one(0L), isAccessible());
    }

    @Test
    @OAuthRequest(username = "someone.else@techdev.de")
    public void oneForbiddenForOther() throws Exception {
        assertThat(one(0L), isForbidden());
    }

    @Test
    public void findByEmployeeOrderByStartDateAscAllowedForEmployee() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/vacationRequests/search/findByEmployeeOrderByStartDateAsc?employee=0", String.class);
        assertThat(response, isAccessible());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR", username = "supervisor@techdev.de")
    public void findByEmployeeOrderByStartDateAscAllowedForSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/vacationRequests/search/findByEmployeeOrderByStartDateAsc?employee=0", String.class);
        assertThat(response, isAccessible());
    }

    /**
     * Access is forbidden, but currently spring-data-rest will throw a 400 because the employee cannot be unmarshalled from the id.
     */
    @Test
    @Ignore
    @OAuthRequest(username = "someone.else@techdev.de")
    public void findByEmployeeOrderByStartDateAscForbiddenForOther() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/vacationRequests/search/findByEmployeeOrderByStartDateAsc?employee=0", String.class);
        assertThat(response, isForbidden());
    }

    @Test
    public void createAllowedForEmployee() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).build();
        assertThat(create(json), isCreated());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR", username = "supervisor@techdev.de")
    public void createForbiddenForSupervisorIfNotOwner() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).build();
        assertThat(create(json), isForbidden());
    }

    @Test
    public void updateForbiddenForEmployee() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).apply(v -> v.setId(0L)).build();
        assertThat(update(0L, json), isForbidden());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR", username = "supervisor@techdev.de")
    public void updateAllowedForSupervisor() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).apply(v -> v.setId(0L)).build();
        assertThat(update(0L, json), isUpdated());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void updateSelfNotAllowedForSupervisor() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).apply(v -> v.setId(0L)).build();
        assertThat(update(0L, json), isForbidden());
    }

    /**
     * Access is forbidden, but currently spring-data-rest will throw a 400 because the employee cannot be unmarshalled from the id.
     *
     * @throws Exception
     */
    @Test
    @Ignore
    @OAuthRequest(username = "someone.else@techdev.de")
    public void updateForbiddenForOther() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).apply(v -> v.setId(0L)).build();
        assertThat(update(0L, json), isForbidden());
    }

    @Test
    public void deleteAllowedForEmployee() throws Exception {
        assertThat(remove(0L), isNoContent());
    }

    @Test
    @OAuthRequest("employee2@techdev.de")
    public void deleteApprovedNotAllowedForEmployee() throws Exception {
        assertThat(remove(1L), isForbidden());
    }

    @Test
    @OAuthRequest("employee2@techdev.de")
    public void deleteRejectedNotAllowedForEmployee() throws Exception {
        assertThat(remove(2L), isForbidden());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR", username = "supervisor@techdev.de")
    public void deleteAllowedForSupervisor() throws Exception {
        assertThat(remove(0L), isNoContent());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void deleteForbiddenForOwningSupervisor() throws Exception {
        assertThat(remove(1L), isForbidden());
    }

    @Test
    @OAuthRequest(username = "someone.else@techdev.de")
    public void deleteForbiddenForOther() throws Exception {
        assertThat(remove(0L), isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_ADMIN")
    public void updateEmployeeIsForbidden() throws Exception {
        assertThat(updateLink(0L, "employee", "/employees/0"), isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_ADMIN")
    public void updateApproverIsForbidden() throws Exception {
        assertThat(updateLink(0L, "approver", "/employees/0"), isForbidden());
    }

    @Test
    public void deleteEmployeeIsForbidden() throws Exception {
        assertThat(removeUrl("/vacationRequests/0/employee"), isForbidden());
    }

    @Test
    public void deleteApproverIsForbidden() throws Exception {
        assertThat(removeUrl("/vacationRequests/1/approver"), isForbidden());
    }

    @Test
    @Ignore("See DATAREST-476")
    public void getApprover() throws Exception {
        assertThat(oneUrl("/vacationRequests/1/approver"), isAccessible());
    }

    @Test
    public void findByStatusOrderBySubmissionTimeAscForbiddenForEmployee() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/vacationRequests/search/findByStatusOrderBySubmissionTimeAsc?approved=APPROVED", String.class);
        assertThat(response, isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void findByStatusOrderBySubmissionTimeAscAllowedForSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/vacationRequests/search/findByStatusOrderBySubmissionTimeAsc?approved=APPROVED", String.class);
        assertThat(response, isAccessible());
    }

    @Test
    @OAuthRequest("ROLE_ADMIN")
    public void daysPerEmployeeBetweenAccessibleForAdmin() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/vacationRequests/daysPerEmployeeBetween?start=2014-01-01&end=2014-01-31", String.class);
        assertThat(response, isAccessible());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void daysPerEmployeeBetweenForbiddenForSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/vacationRequests/daysPerEmployeeBetween?start=2014-01-01&end=2014-01-31", String.class);
        assertThat(response, isForbidden());
    }

}

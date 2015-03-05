package de.techdev.trackr.domain.employee.vacation;

import de.techdev.test.TestConstants;
import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractRestIntegrationTest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static de.techdev.test.rest.DomainResourceTestMatchers.isAccessible;
import static de.techdev.test.rest.DomainResourceTestMatchers.isForbidden;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Sql("resourceTest.sql")
@Sql(TestConstants.CREATE_UUID_MAPPING_TABLE_SQL_FILE)
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@OAuthRequest("ROLE_SUPERVISOR")
public class VacationRequestControllerSecurityTest extends AbstractRestIntegrationTest {

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void approveNotAllowedForSupervisorOnOwnVacationRequest() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(host + "/vacationRequests/0/approve", HttpMethod.PUT, HttpEntity.EMPTY, String.class);
        assertThat(response, isForbidden());
        ResponseEntity<VacationRequest> vacationRequest = restTemplate.getForEntity(host + "/vacationRequests/0", VacationRequest.class);
        assertThat(vacationRequest.getBody().getStatus(), is(VacationRequest.VacationRequestStatus.PENDING));
    }

    @Test
    @OAuthRequest
    public void approveNotAllowedForEmployees() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(host + "/vacationRequests/0/approve", HttpMethod.PUT, HttpEntity.EMPTY, String.class);
        assertThat(response, isForbidden());
        ResponseEntity<VacationRequest> vacationRequest = restTemplate.getForEntity(host + "/vacationRequests/0", VacationRequest.class);
        assertThat(vacationRequest.getBody().getStatus(), is(VacationRequest.VacationRequestStatus.PENDING));
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR", username = "supervisor@techdev.de")
    public void approveAllowedForOtherSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(host + "/vacationRequests/0/approve", HttpMethod.PUT, HttpEntity.EMPTY, String.class);
        assertThat(response, isAccessible());
        ResponseEntity<VacationRequest> vacationRequest = restTemplate.getForEntity(host + "/vacationRequests/0", VacationRequest.class);
        assertThat(vacationRequest.getBody().getStatus(), is(VacationRequest.VacationRequestStatus.APPROVED));
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR", username = "supervisor@techdev.de")
    public void rejectAllowedForSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(host + "/vacationRequests/0/reject", HttpMethod.PUT, HttpEntity.EMPTY, String.class);
        assertThat(response, isAccessible());
        ResponseEntity<VacationRequest> vacationRequest = restTemplate.getForEntity(host + "/vacationRequests/0", VacationRequest.class);
        assertThat(vacationRequest.getBody().getStatus(), is(VacationRequest.VacationRequestStatus.REJECTED));
    }

    @Test
    public void selfRejectForbiddenForSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(host + "/vacationRequests/0/reject", HttpMethod.PUT, HttpEntity.EMPTY, String.class);
        assertThat(response, isForbidden());
        ResponseEntity<VacationRequest> vacationRequest = restTemplate.getForEntity(host + "/vacationRequests/0", VacationRequest.class);
        assertThat(vacationRequest.getBody().getStatus(), is(VacationRequest.VacationRequestStatus.PENDING));
    }
}

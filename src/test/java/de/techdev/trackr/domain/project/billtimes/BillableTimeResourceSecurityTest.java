package de.techdev.trackr.domain.project.billtimes;

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
public class BillableTimeResourceSecurityTest extends AbstractDomainResourceSecurityTest {

    private BillableTimesJsonGenerator jsonGenerator = new BillableTimesJsonGenerator();

    @Override
    protected String getResourceName() {
        return "billableTimes";
    }

    @Test
    public void rootNotExported() throws Exception {
        assertThat(root(), isMethodNotAllowed());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void oneAllowedForSupervisor() throws Exception {
        assertThat(one(0L), isAccessible());
    }

    @Test
    public void oneForbiddenForEmployee() throws Exception {
        assertThat(one(0L), isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void createAllowedForSupervisor() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).withProjectId(0L).build();
        assertThat(create(json), isCreated());
    }

    @Test
    public void createForbiddenForEmployee() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).withProjectId(0L).build();
        assertThat(create(json), isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void deleteAllowedForSupervisor() throws Exception {
        assertThat(remove(0L), isNoContent());
    }

    @Test
    public void deleteForbiddenForEmployee() throws Exception {
        assertThat(remove(0L), isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void updateAllowedForSupervisor() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).withProjectId(0L).apply(b -> b.setId(0L)).build();
        assertThat(update(0L, json), isUpdated());
    }

    /**
     * TODO: this fails with HTTP 400 because findOne is annotated with @PreAuthorize and spring-data-rest doesn't forward the AccessDeniedException correctly
     */
    @Test
    @Ignore
    public void updateForbiddenForEmployee() throws Exception {
        String json = jsonGenerator.start().withEmployeeId(0L).withProjectId(0L).apply(b -> b.setId(0L)).build();
        assertThat(update(0L, json), isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_ADMIN")
    public void deleteEmployeeForbidden() throws Exception {
        assertThat(removeUrl("/billableTimes/0/employee"), isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_ADMIN")
    public void deleteProjectForbidden() throws Exception {
        assertThat(removeUrl("/billableTimes/0/project"), isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void updateEmployeeAllowedForSupervisor() throws Exception {
        assertThat(updateLink(0L, "employee", "/employees/0"), isNoContent());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void updateProjectAllowedForSupervisor() throws Exception {
        assertThat(updateLink(0L, "project", "/projects/0"), isNoContent());
    }

    @Test
    public void updateEmployeeForbiddenForEmployee() throws Exception {
        assertThat(updateLink(0L, "employee", "/employees/0"), isForbidden());
    }

    @Test
    public void updateProjectForbiddenForEmployee() throws Exception {
        assertThat(updateLink(0L, "project", "/projects/0"), isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_ADMIN")
    public void findByDateBetweenAllowedForAdmin() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/billableTimes/search/findByDateBetween?start=2014-01-01&end=2014-01-31", String.class);
        assertThat(response, isAccessible());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void findByDateBetweenForbiddenForSupervisor() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(host + "/billableTimes/search/findByDateBetween?start=2014-01-01&end=2014-01-31", String.class);
        assertThat(response, isForbidden());
    }
}

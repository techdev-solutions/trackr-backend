package de.techdev.trackr.domain.project;

import de.techdev.test.oauth.OAuthRequest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static de.techdev.test.rest.DomainResourceTestMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Sql("resourceTest.sql")
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@OAuthRequest("ROLE_ADMIN")
public class ProjectResourceSecurityTest extends AbstractDomainResourceSecurityTest {

    private ProjectJsonGenerator jsonGenerator = new ProjectJsonGenerator();

    @Override
    protected String getResourceName() {
        return "projects";
    }

    @Test
    @OAuthRequest
    public void rootAccessible() throws Exception {
        assertThat(root(), isAccessible());
    }

    @Test
    @OAuthRequest
    public void one() throws Exception {
        assertThat(one(0L), isAccessible());
    }

    @Test
    public void createAllowedForAdmin() throws Exception {
        String json = jsonGenerator.start().build();
        assertThat(create(json), isCreated());
    }

    @Test
    public void updateAllowedForAdmin() throws Exception {
        String json = jsonGenerator.start().apply(p -> p.setId(0L)).build();
        assertThat(update(0L, json), isUpdated());
    }

    @Test
    public void deleteAllowedForAdmin() throws Exception {
        assertThat(remove(0L), isNoContent());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void createForbiddenForSupervisor() throws Exception {
        String json = jsonGenerator.start().build();
        assertThat(create(json), isForbidden());
    }

    @Test
    @OAuthRequest(value = "ROLE_SUPERVISOR")
    public void updateForbiddenForSupervisor() throws Exception {
        String json = jsonGenerator.start().apply(p -> p.setId(0L)).build();
        assertThat(update(0L, json), isForbidden());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void deleteForbiddenForSupervisor() throws Exception {
        assertThat(remove(0L), isForbidden());
    }

    @Test
    public void setCompanyAllowedForAdmin() throws Exception {
        assertThat(updateLink(0L, "company", "/companies/0"), isNoContent());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void setCompanyForbiddenForSupervisor() throws Exception {
        assertThat(updateLink(0L, "company", "/companies/0"), isForbidden());
    }

    @Test
    public void setDebitorAllowedForAdmin() throws Exception {
        assertThat(updateLink(0L, "debitor", "/companies/0"), isNoContent());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void setDebitorForbiddenForSupervisor() throws Exception {
        assertThat(updateLink(0L, "debitor", "/companies/0"), isForbidden());
    }

    @Test
    public void setWorktimesAllowedForAdmin() throws Exception {
        assertThat(updateLink(0L, "workTimes", "/workTimes/0"), isNoContent());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void setWorktimesForbiddenForSupervisor() throws Exception {
        assertThat(updateLink(0L, "workTimes", "/workTimes/0"), isForbidden());
    }

    @Test
    public void deleteCompanyAllowedForAdmin() throws Exception {
        assertThat(removeUrl("/projects/0/company"), isNoContent());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void deleteCompanyForbiddenForSupervisor() throws Exception {
        assertThat(removeUrl("/projects/0/company"), isForbidden());
    }

    @Test
    public void deleteDebitorAllowedForAdmin() throws Exception {
        assertThat(removeUrl("/projects/0/debitor"), isNoContent());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void deleteDebitorForbiddenForSupervisor() throws Exception {
        assertThat(removeUrl("/projects/0/debitor"), isForbidden());
    }

    @Test
    public void deleteWorktimesAllowedForAdmin() throws Exception {
        assertThat(removeUrl("/projects/0/workTimes/0"), isNoContent());
    }

    @Test
    @OAuthRequest("ROLE_SUPERVISOR")
    public void deleteWorktimesForbiddenForSupervisor() throws Exception {
        assertThat(removeUrl("/projects/0/workTimes/0"), isForbidden());
    }

    public Project getNewTransientObject(int i) {
        Project project = new Project();
        project.setIdentifier("identifier_" + i);
        project.setName("name_" + i);
        project.setDailyRate(BigDecimal.TEN.multiply(new BigDecimal(i)));
        project.setFixedPrice(BigDecimal.TEN.multiply(new BigDecimal(i)));
        project.setHourlyRate(BigDecimal.TEN.multiply(new BigDecimal(i)));
        project.setVolume(i);
        return project;
    }
}

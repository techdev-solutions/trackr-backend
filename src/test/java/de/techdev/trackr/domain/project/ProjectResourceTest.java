package de.techdev.trackr.domain.project;

import de.techdev.trackr.domain.AbstractDomainResourceTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

import static de.techdev.trackr.domain.DomainResourceTestMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class ProjectResourceTest extends AbstractDomainResourceTest<Project> {

    @Autowired
    private WorkTimeDataOnDemand workTimeDataOnDemand;

    @Before
    public void setUp() throws Exception {
        workTimeDataOnDemand.init();
    }

    @Override
    protected String getResourceName() {
        return "projects";
    }

    @Test
    public void root() throws Exception {
        assertThat(root(employeeSession()), isAccessible());
    }

    @Test
    public void one() throws Exception {
        assertThat(one(employeeSession()), isAccessible());
    }

    @Test
    public void createAllowedForAdmin() throws Exception {
        assertThat(create(adminSession()), isCreated());
    }

    @Test
    public void updateAllowedForAdmin() throws Exception {
        assertThat(update(adminSession()), isUpdated());
    }

    @Test
    public void deleteAllowedForAdmin() throws Exception {
        assertThat(remove(adminSession()), isNoContent());
    }

    @Test
    public void createForbiddenForSupervisor() throws Exception {
        assertThat(create(supervisorSession()), isForbidden());
    }

    @Test
    public void updateForbiddenForSupervisor() throws Exception {
        assertThat(update(supervisorSession()), isForbidden());
    }

    @Test
    public void deleteForbiddenForSupervisor() throws Exception {
        assertThat(remove(supervisorSession()), isForbidden());
    }

    @Test
    public void setCompanyAllowedForAdmin() throws Exception {
        assertThat(updateLink(adminSession(), "company", "/companies/0"), isNoContent());
    }

    @Test
    public void setCompanyForbiddenForSupervisor() throws Exception {
        assertThat(updateLink(supervisorSession(), "company", "/companies/0"), isForbidden());
    }

    @Test
    public void setDebitorAllowedForAdmin() throws Exception {
        assertThat(updateLink(adminSession(), "debitor", "/companies/0"), isNoContent());
    }

    @Test
    public void setDebitorForbiddenForSupervisor() throws Exception {
        assertThat(updateLink(supervisorSession(), "debitor", "/companies/0"), isForbidden());
    }

    @Test
    public void setWorktimesAllowedForAdmin() throws Exception {
        assertThat(updateLink(adminSession(), "workTimes", "/workTimes/0"), isNoContent());
    }

    @Test
    public void setWorktimesForbiddenForSupervisor() throws Exception {
        assertThat(updateLink(supervisorSession(), "workTimes", "/workTimes/0"), isForbidden());
    }

    @Test
    public void deleteCompanyAllowedForAdmin() throws Exception {
        Project project = dataOnDemand.getRandomObject();
        assertThat(removeUrl(adminSession(), "/projects/" + project.getId() + "/company"), isNoContent());
    }

    @Test
    public void deleteCompanyForbiddenForSupervisor() throws Exception {
        Project project = dataOnDemand.getRandomObject();
        assertThat(removeUrl(supervisorSession(), "/projects/" + project.getId() + "/company"), isForbidden());
    }

    @Test
    public void deleteDebitorAllowedForAdmin() throws Exception {
        Project project = dataOnDemand.getRandomObject();
        assertThat(removeUrl(adminSession(), "/projects/" + project.getId() + "/debitor"), isNoContent());
    }

    @Test
    @Ignore
    public void deleteDebitorForbiddenForSupervisor() throws Exception {
        Project project = dataOnDemand.getRandomObject();
        assertThat(removeUrl(supervisorSession(), "/projects/" + project.getId() + "/debitor"), isForbidden());
    }

    @Test
    public void deleteWorktimesAllowedForAdmin() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        assertThat(removeUrl(adminSession(), "/projects/" + workTime.getProject().getId() + "/workTimes/" + workTime.getId()), isNoContent());
    }

    @Test
    public void deleteWorktimesForbiddenForSupervisor() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        assertThat(removeUrl(supervisorSession(), "/projects/" + workTime.getProject().getId() + "/workTimes/" + workTime.getId()), isForbidden());
    }

    @Override
    protected String getJsonRepresentation(Project project) {
        StringWriter writer = new StringWriter();
        JsonGenerator jg = jsonGeneratorFactory.createGenerator(writer);
        jg.writeStartObject()
          .write("name", project.getName())
          .write("identifier", project.getIdentifier())
          .write("volume", project.getVolume())
          .write("hourlyCostRate", project.getDailyRate())
          .write("salary", project.getHourlyRate())
          .write("title", project.getFixedPrice())
          .write("company", "/api/companies/" + project.getCompany().getId());

        if (project.getDebitor() != null) {
            jg.write("debitor", "/api/companies/" + project.getDebitor().getId());
        }
        if (project.getId() != null) {
            jg.write("id", project.getId());
        }
        jg.writeEnd().close();
        return writer.toString();
    }
}

package de.techdev.trackr.domain.project;

import de.techdev.trackr.core.web.MockMvcTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.stream.JsonGenerator;
import java.io.StringWriter;

import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Moritz Schulze
 */
public class ProjectResourceTest extends MockMvcTest {

    @Autowired
    private ProjectDataOnDemand projectDataOnDemand;

    @Autowired
    private WorkTimeDataOnDemand workTimeDataOnDemand;

    @Before
    public void setUp() throws Exception {
        projectDataOnDemand.init();
        workTimeDataOnDemand.init();
    }

    @Test
    public void root() throws Exception {
        mockMvc.perform(
                get("/projects")
                        .session(employeeSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType))
               .andExpect(jsonPath("_embedded.projects[0].id", isNotNull()));
    }

    @Test
    public void one() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                get("/projects/" + project.getId())
                        .session(employeeSession()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(standardContentType))
               .andExpect(jsonPath("id", is(project.getId().intValue())));
    }

    @Test
    public void createAllowedForAdmin() throws Exception {
        Project project = projectDataOnDemand.getNewTransientObject(500);
        mockMvc.perform(
                post("/projects")
                        .session(adminSession())
                        .content(generateProjectJson(project)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("id", isNotNull()));
    }

    @Test
    public void updateAllowedForAdmin() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/projects/" + project.getId())
                        .session(adminSession())
                        .content(generateProjectJson(project)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("id", isNotNull()));
    }

    @Test
    public void deleteAllowedForAdmin() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/projects/" + project.getId())
                        .session(adminSession()))
               .andExpect(status().isNoContent());
    }

    @Test
    public void createForbiddenForSupervisor() throws Exception {
        Project project = projectDataOnDemand.getNewTransientObject(500);
        mockMvc.perform(
                post("/projects")
                        .session(supervisorSession())
                        .content(generateProjectJson(project)))
               .andExpect(status().isForbidden());
    }

    @Test
    public void updateForbiddenForSupervisor() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/projects/" + project.getId())
                        .session(supervisorSession())
                        .content(generateProjectJson(project)))
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteForbiddenForSupervisor() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/projects/" + project.getId())
                        .session(supervisorSession()))
               .andExpect(status().isForbidden());
    }

    @Test
    public void setCompanyAllowedForAdmin() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/projects/" + project.getId() + "/company")
                        .session(adminSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/companies/" + 0))
                .andExpect(status().isNoContent());
    }

    @Test
    public void setCompanyForbiddenForSupervisor() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/projects/" + project.getId() + "/company")
                        .session(supervisorSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/companies/" + 0))
               .andExpect(status().isForbidden());
    }

    @Test
    public void setDebitorAllowedForAdmin() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/projects/" + project.getId() + "/debitor")
                        .session(adminSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/companies/" + 0))
               .andExpect(status().isNoContent());
    }

    @Test
    public void setDebitorForbiddenForSupervisor() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/projects/" + project.getId() + "/debitor")
                        .session(supervisorSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/companies/" + 0))
               .andExpect(status().isForbidden());
    }

    @Test
    public void setWorktimesAllowedForAdmin() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/projects/" + project.getId() + "/workTimes")
                        .session(adminSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/workTimes/" + 0))
               .andExpect(status().isNoContent());
    }

    @Test
    public void setWorktimesForbiddenForSupervisor() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                put("/projects/" + project.getId() + "/workTimes")
                        .session(supervisorSession())
                        .header("Content-Type", "text/uri-list")
                        .content("/workTimes/" + 0))
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteCompanyAllowedForAdmin() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/projects/" + project.getId() + "/company")
                        .session(adminSession()))
               .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCompanyForbiddenForSupervisor() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/projects/" + project.getId() + "/company")
                        .session(supervisorSession()))
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteDebitorAllowedForAdmin() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/projects/" + project.getId() + "/debitor")
                        .session(adminSession()))
               .andExpect(status().isNoContent());
    }

    @Test
    @Ignore
    public void deleteDebitorForbiddenForSupervisor() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/projects/" + project.getId() + "/debitor")
                        .session(supervisorSession()))
               .andExpect(status().isForbidden());
    }

    @Test
    public void deleteWorktimesAllowedForAdmin() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/projects/" + workTime.getProject().getId() + "/workTimes/" + workTime.getId())
                        .session(adminSession()))
               .andExpect(status().isNoContent());
    }

    @Test
    public void deleteWorktimesForbiddenForSupervisor() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        mockMvc.perform(
                delete("/projects/" + workTime.getProject().getId() + "/workTimes/" + workTime.getId())
                        .session(supervisorSession()))
               .andExpect(status().isForbidden());
    }

    protected String generateProjectJson(Project project) {
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

package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Project;
import de.techdev.trackr.domain.support.ProjectDataOnDemand;
import de.techdev.trackr.web.MockMvcTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Moritz Schulze
 */
public class ProjectResourceTest extends MockMvcTest {

    @Autowired
    private ProjectDataOnDemand projectDataOnDemand;

    @Before
    public void setUp() throws Exception {
        projectDataOnDemand.init();
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
}

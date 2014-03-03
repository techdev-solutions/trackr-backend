package de.techdev.trackr.repository;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.domain.Project;
import de.techdev.trackr.domain.support.ProjectDataOnDemand;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Moritz Schulze
 */
public class ProjectRepositoryTest extends TransactionalIntegrationTest {

    @Autowired
    private ProjectDataOnDemand projectDataOnDemand;

    @Autowired
    private ProjectRepository projectRepository;

    @Before
    public void setUp() throws Exception {
        projectDataOnDemand.init();
    }

    @Test
    public void one() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        Project one = projectRepository.findOne(project.getId());
        assertThat(one.getId(), is(project.getId()));
    }

    @Test
    public void all() throws Exception {
        List<Project> all = projectRepository.findAll();
        assertThat(all, isNotEmpty());
    }

    @Test
    public void findByIdentifier() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        Project byIdentifier = projectRepository.findByIdentifier(project.getIdentifier());
        assertThat(byIdentifier.getId(), is(project.getId()));
    }
}

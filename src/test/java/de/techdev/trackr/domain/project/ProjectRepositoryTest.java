package de.techdev.trackr.domain.project;

import de.techdev.trackr.TransactionalIntegrationTest;
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

    @Test
    public void findByNameLikeOrIdentifierLikeOrderByNameAscOnlyIdentifier() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        List<Project> all = projectRepository.findByNameLikeIgnoreCaseOrIdentifierLikeIgnoreCaseOrderByNameAsc(project.getIdentifier(), project.getIdentifier());
        assertThat(all, isNotEmpty());
    }

    @Test
    public void findByNameLikeOrIdentifierLikeOrderByNameAscOnlyName() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        List<Project> all = projectRepository.findByNameLikeIgnoreCaseOrIdentifierLikeIgnoreCaseOrderByNameAsc(project.getName(), project.getName());
        assertThat(all, isNotEmpty());
    }
}

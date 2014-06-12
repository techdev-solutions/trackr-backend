package de.techdev.trackr.domain.company;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.domain.project.Project;
import de.techdev.trackr.domain.project.ProjectDataOnDemand;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.echocat.jomon.testing.BaseMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class CompanyRepositoryTest extends TransactionalIntegrationTest {

    @Autowired
    private CompanyDataOnDemand companyDataOnDemand;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ContactPersonDataOnDemand contactPersonDataOnDemand;

    @Autowired
    private ProjectDataOnDemand projectDataOnDemand;

    @Test
    public void findById() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        Company one = companyRepository.findOne(company.getId());
        assertThat(one, isNotNull());
    }

    @Test
    public void findAll() throws Exception {
        companyDataOnDemand.init();
        List<Company> all = companyRepository.findAll();
        assertThat(all, isNotEmpty());
    }

    @Test
    public void findByCompanyId() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        Company byCompanyId = companyRepository.findByCompanyId(company.getCompanyId());
        assertThat(byCompanyId.getId(), is(company.getId()));
    }

    @Test
    public void findByNameLikeOrderByNameAsc() throws Exception {
        Company company = companyDataOnDemand.getRandomObject();
        List<Company> byNameLikeOrderByNameAsc = companyRepository.findByNameLikeIgnoreCaseOrderByNameAsc(company.getName());
        assertThat(byNameLikeOrderByNameAsc, isNotEmpty());
    }

    @Test
    public void deleteWithContactPersons() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        companyRepository.delete(contactPerson.getCompany());
    }

    @Test
    public void deleteWithProject() throws Exception {
        Project project = projectDataOnDemand.getRandomObject();
        companyRepository.delete(project.getCompany());
    }
}

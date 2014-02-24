package de.techdev.trackr.repository;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.domain.Company;
import de.techdev.trackr.domain.ContactPerson;
import de.techdev.trackr.domain.support.CompanyDataOnDemand;
import de.techdev.trackr.domain.support.ContactPersonDataOnDemand;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(propagation = Propagation.NEVER) //TODO: whithout this and no cascade on the entity the test does not fail, why?
    public void deleteWithContactPersons() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        companyRepository.delete(contactPerson.getCompany());
    }
}

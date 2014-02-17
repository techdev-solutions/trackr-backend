package de.techdev.trackr.repository;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.domain.Company;
import de.techdev.trackr.domain.support.CompanyDataOnDemand;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class CompanyRepositoryTest extends TransactionalIntegrationTest {

    @Autowired
    private CompanyDataOnDemand companyDataOnDemand;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void findById() throws Exception {
        Company company = companyDataOnDemand.getRandomCompany();
        Company one = companyRepository.findOne(company.getId());
        assertThat(one, isNotNull());
    }

    @Test
    public void findAll() throws Exception {
        companyDataOnDemand.init();
        List<Company> all = companyRepository.findAll();
        assertThat(all, isNotEmpty());
    }
}

package de.techdev.trackr.domain.company;

import de.techdev.test.TransactionalIntegrationTest;
import de.techdev.test.rest.AbstractDomainResourceSecurityTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@Sql("repositoryTest.sql")
@Sql(value = AbstractDomainResourceSecurityTest.EMPTY_DATABASE_FILE, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CompanyRepositoryTest extends TransactionalIntegrationTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void deleteWithContactPersons() throws Exception {
        companyRepository.delete(0L);
    }

    @Test
    public void deleteWithProject() throws Exception {
        companyRepository.delete(1L);
    }
}

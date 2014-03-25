package de.techdev.trackr.domain.employee.login;

import de.techdev.trackr.TransactionalIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class AuthorityRepositoryTest extends TransactionalIntegrationTest {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AuthorityDataOnDemand authorityDataOnDemand;

    @Test
    public void findById() throws Exception {
        Authority authority = authorityDataOnDemand.getRandomObject();
        Authority one = authorityRepository.findOne(authority.getId());
        assertThat(one, isNotNull());
    }

    @Test
    public void findAll() throws Exception {
        authorityDataOnDemand.init();
        assertThat(authorityRepository.findAll(), isNotEmpty());
    }

    @Test
    public void findByAuthority() throws Exception {
        Authority authority = authorityDataOnDemand.getRandomObject();
        Authority one = authorityRepository.findByAuthority(authority.getAuthority());
        assertThat(one, isNotNull());
    }
}

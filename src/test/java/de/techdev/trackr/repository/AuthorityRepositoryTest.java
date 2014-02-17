package de.techdev.trackr.repository;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.domain.Authority;
import de.techdev.trackr.domain.support.AuthorityDataOnDemand;
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
        Authority authority = authorityDataOnDemand.getRandomAuthority();
        Authority one = authorityRepository.findOne(authority.getId());
        assertThat(one, isNotNull());
    }

    @Test
    public void findAll() throws Exception {
        authorityDataOnDemand.init();
        assertThat(authorityRepository.findAll(), isNotEmpty());
    }
}

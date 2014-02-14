package de.techdev.trackr.repository;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.domain.Credentials;
import de.techdev.trackr.domain.support.CredentialsDataOnDemand;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class CredentialsRepositoryTest extends TransactionalIntegrationTest {

    @Autowired
    private CredentialsDataOnDemand credentialsDataOnDemand;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Test
    public void findByEmail() throws Exception {
        Credentials credentials = credentialsDataOnDemand.getRandomCredentials();
        assertThat(credentialsRepository.findByEmail(credentials.getEmail()), isNotNull());
    }

    @Test
    public void findAll() throws Exception {
        credentialsDataOnDemand.init();
        assertThat(credentialsRepository.findAll(), isNotEmpty());
    }
}

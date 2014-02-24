package de.techdev.trackr.repository;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.domain.Credential;
import de.techdev.trackr.domain.support.CredentialDataOnDemand;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class CredentialRepositoryTest extends TransactionalIntegrationTest {

    @Autowired
    private CredentialDataOnDemand credentialDataOnDemand;

    @Autowired
    private CredentialRepository credentialRepository;

    @Test
    public void findById() throws Exception {
        Credential credential = credentialDataOnDemand.getRandomObject();
        Credential one = credentialRepository.findOne(credential.getId());
        assertThat(one, isNotNull());
    }

    @Test
    public void findByEmail() throws Exception {
        Credential credential = credentialDataOnDemand.getRandomObject();
        assertThat(credentialRepository.findByEmail(credential.getEmail()), isNotNull());
    }

    @Test
    public void findAll() throws Exception {
        credentialDataOnDemand.init();
        assertThat(credentialRepository.findAll(), isNotEmpty());
    }
}

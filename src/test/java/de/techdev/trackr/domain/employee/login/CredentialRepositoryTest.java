package de.techdev.trackr.domain.employee.login;

import de.techdev.trackr.TransactionalIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @Autowired
    private AuthorityDataOnDemand authorityDataOnDemand;

    @Before
    public void setUp() throws Exception {
        credentialDataOnDemand.init();
    }

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
        assertThat(credentialRepository.findAll(), isNotEmpty());
    }

    @Test
    public void findByAuthoritiesContaining() throws Exception {
        Authority authority = authorityDataOnDemand.getRandomObject();
        Credential credential = credentialDataOnDemand.getRandomObject();
        credential.getAuthorities().add(authority);
        credentialRepository.save(credential);
        List<Credential> all = credentialRepository.findByAuthorities(authority);
        assertThat(all, isNotEmpty());
    }
}

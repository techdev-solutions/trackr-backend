package de.techdev.trackr.domain.company;

import de.techdev.trackr.TransactionalIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class ContactPersonRepositoryTest extends TransactionalIntegrationTest {

    @Autowired
    private ContactPersonDataOnDemand contactPersonDataOnDemand;

    @Autowired
    private ContactPersonRepository contactPersonRepository;

    @Test
    public void one() throws Exception {
        ContactPerson contactPerson = contactPersonDataOnDemand.getRandomObject();
        ContactPerson one = contactPersonRepository.findOne(contactPerson.getId());
        assertThat(one, isNotNull());
    }

    @Test
    public void all() throws Exception {
        contactPersonDataOnDemand.init();
        Iterable<ContactPerson> all = contactPersonRepository.findAll();
        assertThat(all, isNotEmpty());
    }
}

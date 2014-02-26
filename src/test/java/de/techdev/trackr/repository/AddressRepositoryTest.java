package de.techdev.trackr.repository;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.domain.Address;
import de.techdev.trackr.domain.support.AddressDataOnDemand;
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
public class AddressRepositoryTest extends TransactionalIntegrationTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressDataOnDemand addressDataOnDemand;

    @Before
    public void setUp() throws Exception {
        addressDataOnDemand.init();
    }

    @Test
    public void one() throws Exception {
        Address address = addressDataOnDemand.getRandomObject();
        Address one = addressRepository.findOne(address.getId());
        assertThat(one, isNotNull());
    }

    @Test
    public void all() throws Exception {
        List<Address> all = addressRepository.findAll();
        assertThat(all, isNotEmpty());
    }
}

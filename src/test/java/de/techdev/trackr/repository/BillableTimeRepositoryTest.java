package de.techdev.trackr.repository;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.domain.BillableTime;
import de.techdev.trackr.domain.support.BillableTimeDataOnDemand;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class BillableTimeRepositoryTest extends TransactionalIntegrationTest {

    @Autowired
    private BillableTimeRepository billableTimeRepository;

    @Autowired
    private BillableTimeDataOnDemand billableTimeDataOnDemand;

    @Before
    public void setUp() throws Exception {
        billableTimeDataOnDemand.init();
    }

    @Test
    public void all() throws Exception {
        Iterable<BillableTime> all = billableTimeRepository.findAll();
        assertThat(all, isNotEmpty());
    }

    @Test
    public void one() throws Exception {
        BillableTime billableTime = billableTimeDataOnDemand.getRandomObject();
        BillableTime one = billableTimeRepository.findOne(billableTime.getId());
        assertThat(one, isNotNull());
    }
}

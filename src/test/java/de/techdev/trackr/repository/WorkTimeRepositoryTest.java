package de.techdev.trackr.repository;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.domain.WorkTime;
import de.techdev.trackr.domain.support.WorkTimeDataOnDemand;
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
public class WorkTimeRepositoryTest extends TransactionalIntegrationTest {

    @Autowired
    private WorkTimeRepository workTimeRepository;

    @Autowired
    private WorkTimeDataOnDemand workTimeDataOnDemand;

    @Before
    public void setUp() throws Exception {
        workTimeDataOnDemand.init();
    }

    @Test
    public void one() throws Exception {
        WorkTime workTime = workTimeDataOnDemand.getRandomObject();
        WorkTime one = workTimeRepository.findOne(workTime.getId());
        assertThat(one, isNotNull());
    }

    @Test
    public void all() throws Exception {
        List<WorkTime> all = workTimeRepository.findAll();
        assertThat(all, isNotEmpty());
    }
}

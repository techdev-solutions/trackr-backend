package de.techdev.trackr.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Moritz Schulze
 */
@ContextConfiguration(classes = {ScheduledJobsConfiguration.class, JpaConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ScheduledJobsConfigurationIntegrationTest {

    @Test
    public void bootsUp() throws Exception {

    }
}

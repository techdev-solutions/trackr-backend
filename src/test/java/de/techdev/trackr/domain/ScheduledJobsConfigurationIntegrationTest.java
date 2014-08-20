package de.techdev.trackr.domain;

import de.techdev.trackr.IntegrationTest;
import de.techdev.trackr.core.mail.MailConfiguration;
import de.techdev.trackr.domain.scheduling.ScheduledJobsConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Moritz Schulze
 */
@ContextConfiguration(classes = {ScheduledJobsConfiguration.class, ApiBeansConfiguration.class, MailConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ScheduledJobsConfigurationIntegrationTest extends IntegrationTest {

    @Test
    public void bootsUp() throws Exception {

    }
}

package de.techdev.trackr.core.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Moritz Schulze
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MailConfiguration.class)
@ActiveProfiles("dev")
public class BootMailConfigurationTest {

    @Test
    public void boot() throws Exception {

    }
}

package de.techdev.trackr;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Base class for integration tests that loads the application context.
 * @author Moritz Schulze
 */
@WebAppConfiguration
@ContextConfiguration(classes = {TrackrApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class IntegrationTest {
}

package de.techdev.trackr;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base class for integration tests that loads the application context.
 * @author Moritz Schulze
 */
@ContextConfiguration(classes = {JpaConfiguration.class, DataOnDemandConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class IntegrationTest {
}

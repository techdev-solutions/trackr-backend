package de.techdev.trackr;

import de.techdev.trackr.domain.DataOnDemandConfiguration;
import de.techdev.trackr.domain.JpaConfiguration;
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

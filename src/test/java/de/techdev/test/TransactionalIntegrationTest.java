package de.techdev.test;

import de.techdev.trackr.Trackr;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * A test that should rollback all transactions after finishing.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Trackr.class)
@ActiveProfiles(value = {"in-memory-database"})
@Transactional
@TransactionConfiguration
public abstract class TransactionalIntegrationTest {
}

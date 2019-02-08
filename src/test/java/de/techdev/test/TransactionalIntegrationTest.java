package de.techdev.test;

import de.techdev.trackr.Trackr;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * A test that should rollback all transactions after finishing.
 */
@SpringBootTest(classes = { Trackr.class })
@RunWith(SpringRunner.class)
@ActiveProfiles(value = {"in-memory-database"})
@Transactional
@TransactionConfiguration
public abstract class TransactionalIntegrationTest {
}

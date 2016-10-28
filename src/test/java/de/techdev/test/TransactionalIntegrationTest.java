package de.techdev.test;

import de.techdev.trackr.Trackr;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * A test that should rollback all transactions after finishing.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Trackr.class)
@ActiveProfiles(value = {"in-memory-database"})
@Transactional
@Rollback
public abstract class TransactionalIntegrationTest {
}

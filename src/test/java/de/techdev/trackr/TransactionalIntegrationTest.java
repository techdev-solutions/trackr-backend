package de.techdev.trackr;

import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * A test that should rollback all transactions after finishing.
 * @author Moritz Schulze
 */
@Transactional
@TransactionConfiguration
public abstract class TransactionalIntegrationTest extends IntegrationTest {
}

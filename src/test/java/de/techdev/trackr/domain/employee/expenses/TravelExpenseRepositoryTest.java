package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.IntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;

/**
 * @author Moritz Schulze
 */
public class TravelExpenseRepositoryTest extends IntegrationTest {

    @Autowired
    private TravelExpenseDataOnDemand travelExpenseDataOnDemand;

    @Autowired
    private TravelExpenseRepository travelExpenseRepository;

    @Before
    public void setUp() throws Exception {
        travelExpenseDataOnDemand.init();
    }

    @Test
    public void one() throws Exception {
        TravelExpense travelExpense = travelExpenseDataOnDemand.getRandomObject();
        TravelExpense one = travelExpenseRepository.findOne(travelExpense.getId());
        assertThat(one.getId(), is(travelExpense.getId()));
    }

    @Test
    public void all() throws Exception {
        Iterable<TravelExpense> all = travelExpenseRepository.findAll();
        assertThat(all, isNotEmpty());
    }
}

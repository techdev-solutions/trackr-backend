package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.IntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.echocat.jomon.testing.BaseMatchers.isNotEmpty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Moritz Schulze
 */
public class TravelExpenseReportRepositoryTest extends IntegrationTest {

    @Autowired
    private TravelExpenseReportDataOnDemand travelExpenseReportDataOnDemand;

    @Autowired
    private TravelExpenseReportRepository travelExpenseReportRepository;

    @Before
    public void setUp() throws Exception {
        travelExpenseReportDataOnDemand.init();
    }

    @Test
    public void one() throws Exception {
        TravelExpenseReport travelExpenseReport = travelExpenseReportDataOnDemand.getRandomObject();
        TravelExpenseReport one = travelExpenseReportRepository.findOne(travelExpenseReport.getId());
        assertThat(one.getId(), is(travelExpenseReport.getId()));
    }

    @Test
    public void all() throws Exception {
        Iterable<TravelExpenseReport> all = travelExpenseReportRepository.findAll();
        assertThat(all, isNotEmpty());
    }
}

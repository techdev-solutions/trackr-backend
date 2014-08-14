package de.techdev.trackr.domain.employee.expenses;

import de.techdev.trackr.IntegrationTest;
import de.techdev.trackr.domain.employee.expenses.reports.Report;
import de.techdev.trackr.domain.employee.expenses.reports.ReportRepository;
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
    private ReportRepository travelExpenseReportRepository;

    @Before
    public void setUp() throws Exception {
        travelExpenseReportDataOnDemand.init();
    }

    @Test
    public void one() throws Exception {
        Report travelExpenseReport = travelExpenseReportDataOnDemand.getRandomObject();
        Report one = travelExpenseReportRepository.findOne(travelExpenseReport.getId());
        assertThat(one.getId(), is(travelExpenseReport.getId()));
    }

    @Test
    public void all() throws Exception {
        Iterable<Report> all = travelExpenseReportRepository.findAll();
        assertThat(all, isNotEmpty());
    }
}

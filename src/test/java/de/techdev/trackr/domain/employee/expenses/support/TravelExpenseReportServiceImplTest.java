package de.techdev.trackr.domain.employee.expenses.support;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.core.mail.MailConfiguration;
import de.techdev.trackr.domain.ApiBeansConfiguration;
import de.techdev.trackr.domain.employee.expenses.TravelExpenseReport;
import de.techdev.trackr.domain.employee.expenses.TravelExpenseReportService;
import de.techdev.trackr.domain.employee.expenses.TravelExpenseReportStatus;
import de.techdev.trackr.util.LocalDateUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = {MailConfiguration.class, ApiBeansConfiguration.class})
public class TravelExpenseReportServiceImplTest extends TransactionalIntegrationTest {

    @Autowired
    TravelExpenseReportService service;

    @Test
    public void testReject() throws Exception {
        TravelExpenseReport travelExpenseReport = new TravelExpenseReport();
        travelExpenseReport.setStatus(TravelExpenseReportStatus.SUBMITTED);

        TravelExpenseReport result = service.reject(travelExpenseReport);
        assertThat(result.getStatus(), is(TravelExpenseReportStatus.REJECTED));
    }

    @Test
    public void testApprove() throws Exception {
        TravelExpenseReport travelExpenseReport = new TravelExpenseReport();
        travelExpenseReport.setStatus(TravelExpenseReportStatus.SUBMITTED);

        TravelExpenseReport result = service.accept(travelExpenseReport);
        assertThat(result.getStatus(), is(TravelExpenseReportStatus.APPROVED));
    }

    @Test
    public void testSubmit() throws Exception {
        LocalDate localDate = LocalDate.of(2014, 1, 1);
        Date date = LocalDateUtil.fromLocalDate(localDate);
        TravelExpenseReport travelExpenseReport = new TravelExpenseReport();
        travelExpenseReport.setStatus(TravelExpenseReportStatus.PENDING);
        travelExpenseReport.setSubmissionDate(date);

        TravelExpenseReport result = service.submit(travelExpenseReport);
        assertThat(result.getStatus(), is(TravelExpenseReportStatus.SUBMITTED));
        assertThat(result.getSubmissionDate().after(date), is(true));
    }
}
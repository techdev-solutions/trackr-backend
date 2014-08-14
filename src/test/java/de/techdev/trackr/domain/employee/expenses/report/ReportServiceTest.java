package de.techdev.trackr.domain.employee.expenses.report;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.core.mail.MailConfiguration;
import de.techdev.trackr.domain.ApiBeansConfiguration;
import de.techdev.trackr.domain.company.Company;
import de.techdev.trackr.domain.company.CompanyDataOnDemand;
import de.techdev.trackr.domain.employee.expenses.report.ReportDataOnDemand;
import de.techdev.trackr.domain.employee.expenses.reports.Report;
import de.techdev.trackr.domain.employee.expenses.reports.ReportService;
import de.techdev.trackr.util.LocalDateUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = {MailConfiguration.class, ApiBeansConfiguration.class})
public class ReportServiceTest extends TransactionalIntegrationTest {

    @Autowired
    private ReportService service;

    @Autowired
    private ReportDataOnDemand dataOnDemand;

    @Autowired
    private CompanyDataOnDemand companyDataOnDemand;

    @Test
    public void testReject() throws Exception {
        Report travelExpenseReport = new Report();
        travelExpenseReport.setStatus(Report.Status.SUBMITTED);
        Company company = companyDataOnDemand.getRandomObject();
        travelExpenseReport.setDebitor(company);

        Report result = service.reject(travelExpenseReport, "admin@techdev.de");
        assertThat(result.getStatus(), is(Report.Status.REJECTED));
    }

    @Test
    public void testApprove() throws Exception {
        Report travelExpenseReport = new Report();
        travelExpenseReport.setStatus(Report.Status.SUBMITTED);
        Company company = companyDataOnDemand.getRandomObject();
        travelExpenseReport.setDebitor(company);

        Report result = service.accept(travelExpenseReport, "admin@techdev.de");
        assertThat(result.getStatus(), is(Report.Status.APPROVED));
    }

    @Test
    public void testSubmit() throws Exception {
        Report travelExpenseReport = dataOnDemand.getRandomObject();
        LocalDate localDate = LocalDate.of(2014, 1, 1);
        Date date = LocalDateUtil.fromLocalDate(localDate);
        travelExpenseReport.setStatus(Report.Status.PENDING);
        travelExpenseReport.setSubmissionDate(date);

        Report result = service.submit(travelExpenseReport);
        assertThat(result.getStatus(), is(Report.Status.SUBMITTED));
        assertThat(result.getSubmissionDate().after(date), is(true));
    }
}
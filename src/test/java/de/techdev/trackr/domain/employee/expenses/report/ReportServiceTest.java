package de.techdev.trackr.domain.employee.expenses.report;

import de.techdev.trackr.domain.employee.EmployeeRepository;
import de.techdev.trackr.domain.employee.expenses.reports.Report;
import de.techdev.trackr.domain.employee.expenses.reports.ReportNotifyService;
import de.techdev.trackr.domain.employee.expenses.reports.ReportRepository;
import de.techdev.trackr.domain.employee.expenses.reports.ReportService;
import de.techdev.trackr.util.LocalDateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    @InjectMocks
    private ReportService service;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ReportNotifyService reportNotifyService;

    @Before
    public void setUp() throws Exception {
        when(reportRepository.save(any(Report.class))).then(invocation -> invocation.getArguments()[0]);
    }

    @Test
    public void testReject() throws Exception {
        Report travelExpenseReport = new Report();
        travelExpenseReport.setStatus(Report.Status.SUBMITTED);

        Report result = service.reject(travelExpenseReport, "admin@techdev.de");
        assertThat(result.getStatus(), is(Report.Status.REJECTED));
    }

    @Test
    public void testApprove() throws Exception {
        Report travelExpenseReport = new Report();
        travelExpenseReport.setStatus(Report.Status.SUBMITTED);

        Report result = service.accept(travelExpenseReport, "admin@techdev.de");
        assertThat(result.getStatus(), is(Report.Status.APPROVED));
    }

    @Test
    public void testSubmit() throws Exception {
        Report travelExpenseReport = new Report();
        LocalDate localDate = LocalDate.of(2014, 1, 1);
        Date date = LocalDateUtil.fromLocalDate(localDate);
        travelExpenseReport.setStatus(Report.Status.PENDING);
        travelExpenseReport.setSubmissionDate(date);

        Report result = service.submit(travelExpenseReport);
        assertThat(result.getStatus(), is(Report.Status.SUBMITTED));
        assertThat(result.getSubmissionDate().after(date), is(true));
        verify(reportNotifyService, times(1)).sendSubmittedReportMail(eq(travelExpenseReport));
    }
}
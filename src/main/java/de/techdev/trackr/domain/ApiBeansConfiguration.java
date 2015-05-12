package de.techdev.trackr.domain;

import de.techdev.trackr.core.pdf.PdfRenderer;
import de.techdev.trackr.domain.common.UuidMapper;
import de.techdev.trackr.domain.employee.expenses.reports.ReportNotifyService;
import de.techdev.trackr.domain.employee.expenses.reports.ReportService;
import de.techdev.trackr.domain.employee.login.support.SupervisorService;
import de.techdev.trackr.domain.employee.sickdays.SickDaysNotifyService;
import de.techdev.trackr.domain.employee.vacation.HolidayCalculator;
import de.techdev.trackr.domain.employee.vacation.VacationRequestApproveService;
import de.techdev.trackr.domain.employee.vacation.support.VacationRequestEmployeeToDaysTotalService;
import de.techdev.trackr.domain.employee.vacation.support.VacationRequestNotifyService;
import de.techdev.trackr.domain.employee.worktimetracking.WorkTimeTrackingReminderService;
import de.techdev.trackr.domain.project.invoice.ChangeStateService;
import de.techdev.trackr.domain.project.invoice.InvoiceOverdueService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

/**
 * All Beans needed for the API that have nothing to do with web.
 */
@Configuration
@ComponentScan(includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = RepositoryEventHandler.class)
})
public class ApiBeansConfiguration {

    @Bean
    public ReportService travelExpenseReportService() {
        return new ReportService();
    }

    @Bean
    public ReportNotifyService travelExpenseReportNotifyService() {
        return new ReportNotifyService();
    }

    @Bean
    public WorkTimeTrackingReminderService workTimeTrackingReminderService() {
        return new WorkTimeTrackingReminderService();
    }

    @Bean
    public VacationRequestNotifyService vacationRequestNotifyService() {
        return new VacationRequestNotifyService();
    }

    @Bean
    public VacationRequestApproveService vacationRequestService() {
        return new VacationRequestApproveService();
    }

    @Bean
    public VacationRequestEmployeeToDaysTotalService vacationRequestEmployeeToDaysTotalService() {
        return new VacationRequestEmployeeToDaysTotalService();
    }

    @Bean
    public HolidayCalculator holidayCalculator() {
        return new HolidayCalculator();
    }

    @Bean
    public InvoiceOverdueService invoiceOverduer() {
        return new InvoiceOverdueService();
    }

    @Bean
    public ChangeStateService changeStateService() {
        return new ChangeStateService();
    }

    @Bean
    public SickDaysNotifyService sickDaysNotifyService() {
        return new SickDaysNotifyService();
    }

    @Bean
    public SupervisorService supervisorService() {
        return new SupervisorService();
    }

    @Bean
    public PdfRenderer pdfRenderer() {
        return new PdfRenderer();
    }

    @Bean
    public UuidMapper uuidMapper() {
        return new UuidMapper();
    }
}

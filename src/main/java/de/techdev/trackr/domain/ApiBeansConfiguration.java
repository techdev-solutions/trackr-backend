package de.techdev.trackr.domain;

import de.techdev.trackr.domain.employee.expenses.TravelExpenseReportService;
import de.techdev.trackr.domain.employee.expenses.support.TravelExpenseReportServiceImpl;
import de.techdev.trackr.domain.employee.login.DeactivateEmployeesService;
import de.techdev.trackr.domain.employee.login.support.SupervisorService;
import de.techdev.trackr.domain.employee.sickdays.SickDaysNotifyService;
import de.techdev.trackr.domain.employee.vacation.HolidayCalculator;
import de.techdev.trackr.domain.employee.vacation.VacationRequestApproveService;
import de.techdev.trackr.domain.employee.vacation.support.VacationRequestApproveServiceImpl;
import de.techdev.trackr.domain.employee.vacation.support.VacationRequestEmployeeToDaysTotalService;
import de.techdev.trackr.domain.employee.vacation.support.VacationRequestNotifyService;
import de.techdev.trackr.domain.employee.worktimetracking.WorkTimeTrackingReminderService;
import de.techdev.trackr.domain.project.invoice.ChangeStateService;
import de.techdev.trackr.domain.project.invoice.InvoiceOverdueService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * All Beans needed for the API that have nothing to do with web.
 *
 * @author Moritz Schulze
 */
@Configuration
public class ApiBeansConfiguration {

    @Bean
    public TravelExpenseReportService travelExpenseReportService() {
        return new TravelExpenseReportServiceImpl();
    }

    @Bean
    public WorkTimeTrackingReminderService workTimeTrackingReminderService() {
        return new WorkTimeTrackingReminderService();
    }

    @Bean
    public DeactivateEmployeesService deactivateEmployeesService() {
        return new DeactivateEmployeesService();
    }

    @Bean
    public VacationRequestNotifyService vacationRequestNotifyService() {
        return new VacationRequestNotifyService();
    }

    @Bean
    public VacationRequestApproveService vacationRequestService() {
        return new VacationRequestApproveServiceImpl();
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
}

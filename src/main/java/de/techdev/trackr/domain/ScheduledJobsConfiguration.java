package de.techdev.trackr.domain;

import de.techdev.trackr.domain.common.LastWorkdayDayOfMonthTrigger;
import de.techdev.trackr.domain.employee.EmployeeScheduledJob;
import de.techdev.trackr.domain.employee.vacation.VacationRequestScheduledJobs;
import de.techdev.trackr.domain.project.invoice.InvoiceScheduledJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.concurrent.DelegatingSecurityContextScheduledExecutorService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.Arrays.asList;

/**
 * @author Moritz Schulze
 */
@Configuration
@EnableScheduling
public class ScheduledJobsConfiguration implements SchedulingConfigurer {

    @Bean
    public VacationRequestScheduledJobs vacationScheduledJobs() {
        return new VacationRequestScheduledJobs();
    }

    @Bean
    public EmployeeScheduledJob employeeScheduledJob() {
        return new EmployeeScheduledJob();
    }

    @Bean
    public LastWorkdayDayOfMonthTrigger lastWorkdayDayOfMonthTrigger() {
        return new LastWorkdayDayOfMonthTrigger();
    }

    @Bean
    public InvoiceScheduledJob invoiceScheduledJob() {
        return new InvoiceScheduledJob();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        taskRegistrar.addTriggerTask(employeeScheduledJob().sendWorkTimeReminderTask(), lastWorkdayDayOfMonthTrigger());
    }

    /**
     * @return An executor that has admin rights.
     */
    @Bean(destroyMethod = "shutdownNow")
    public Executor taskExecutor() {
        ScheduledExecutorService delegateExecutor = Executors.newSingleThreadScheduledExecutor();
        SecurityContext schedulerContext = createSchedulerSecurityContext();
        return new DelegatingSecurityContextScheduledExecutorService(delegateExecutor, schedulerContext);
    }

    /**
     * @return A security context with an admin authentication token.
     */
    private SecurityContext createSchedulerSecurityContext() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        List<GrantedAuthority> grantedAuthorities = asList((GrantedAuthority) () -> "ROLE_ADMIN");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("admin@techdev.de", "", grantedAuthorities);
        context.setAuthentication(authenticationToken);
        return context;
    }
}

package de.techdev.trackr.domain.employee;

import de.techdev.trackr.domain.employee.login.DeactivateEmployeesService;
import de.techdev.trackr.domain.employee.worktimetracking.WorkTimeTrackingReminderService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Moritz Schulze
 */
@Slf4j
@Setter
public class EmployeeScheduledJob {

    @Autowired
    private DeactivateEmployeesService deactivateEmployeesService;

    @Autowired
    private WorkTimeTrackingReminderService workTimeTrackingReminderService;

    /**
     * Every day at 4am check if employees must be deactivated and do so if necessary.
     */
    @Scheduled(cron = "0 0 4 * * *")
    public void deactivateEmployeesWithLeaveDateToday() {
        deactivateEmployeesService.deactivateEmployeesWithLeaveDateToday();
    }

    /**
     * Task that gets triggered in {@link de.techdev.trackr.domain.ScheduledJobsConfiguration} by a custom trigger.
     * @return A task that remindes employees to track their working times.
     */
    public Runnable sendWorkTimeReminderTask() {
        return workTimeTrackingReminderService::remindEmployeesToTrackWorkTimes;
    }

}

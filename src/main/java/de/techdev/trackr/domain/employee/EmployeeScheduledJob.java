package de.techdev.trackr.domain.employee;

import de.techdev.trackr.domain.common.FederalState;
import de.techdev.trackr.domain.employee.worktimetracking.WorkTimeTrackingReminderService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Setter
public class EmployeeScheduledJob {

    @Autowired
    private WorkTimeTrackingReminderService workTimeTrackingReminderService;

    /**
     * Task that gets triggered in {@link de.techdev.trackr.domain.scheduling.ScheduledJobsConfiguration} by a custom trigger.
     *
     * @param state The federal state to send out reminders for.
     * @return A task that reminds employees in the federal state to track their working times.
     */
    public Runnable sendWorkTimeReminderTask(FederalState state) {
        return () -> workTimeTrackingReminderService.remindEmployeesToTrackWorkTimes(state);
    }

}

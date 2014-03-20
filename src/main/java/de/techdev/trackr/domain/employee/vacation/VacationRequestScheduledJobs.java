package de.techdev.trackr.domain.employee.vacation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Moritz Schulze
 */
public class VacationRequestScheduledJobs {

    @Autowired
    private VacationRequestApproveService vacationRequestApproveService;

    @Scheduled(cron = "0 0 4 * * *")
    public void approveSevenDaysOldVacationRequests() {
        vacationRequestApproveService.approveSevenDayOldRequests();
    }

}

package de.techdev.trackr.domain.employee.vacation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Moritz Schulze
 */
@Slf4j
public class VacationRequestScheduledJobs {

    @Autowired
    private VacationRequestApproveService vacationRequestApproveService;

    @Scheduled(cron = "0 0 4 * * *")
    public void approveSevenDaysOldVacationRequests() {
        log.debug("Executing vacation request approval trigger.");
        vacationRequestApproveService.approveSevenDayOldRequests();
    }

}

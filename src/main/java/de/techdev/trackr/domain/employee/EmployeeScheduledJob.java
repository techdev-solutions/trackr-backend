package de.techdev.trackr.domain.employee;

import de.techdev.trackr.domain.employee.login.DeactivateEmployeesService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Moritz Schulze
 */
@Slf4j
@Setter
public class EmployeeScheduledJob {

    @Autowired
    private DeactivateEmployeesService deactivateEmployeesService;

    /**
     * Every day at 4am check if employees must be deactivated and do so if necessary.
     */
    @Scheduled(cron = "0 0 4 * * *")
    @Transactional
    public void deactivateEmployeesWithLeaveDateToday() {
        deactivateEmployeesService.deactivateEmployeesWithLeaveDateToday();
    }
}

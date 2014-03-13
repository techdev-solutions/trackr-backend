package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Employee;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * @author Moritz Schulze
 */
@Slf4j
@Setter
public class EmployeeScheduledJob {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Every day at 4am check if employees must be deactivated and do so if necessary.
     */
    @Scheduled(cron = "0 0 4 * * *")
    @Transactional
    public void deactivateEmployeesWithLeaveDateToday() {
        LocalDate now = LocalDate.now();
        Instant instant = now.atStartOfDay(ZoneId.systemDefault()).toInstant();
        List<Employee> employeesToDeactivate = employeeRepository.findByLeaveDateAndCredential_Enabled(Date.from(instant), true);
        employeesToDeactivate.forEach(employee -> {
            log.info("Deactivating employee {}", employee);
            employee.getCredential().setEnabled(false);
            employeeRepository.save(employee);
        });
    }
}

package de.techdev.trackr.domain.employee.login;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.EmployeeRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Service to deactivate employees under certain conditions.
 *
 * @author Moritz Schulze
 */
@Slf4j
@Setter
public class DeactivateEmployeesService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Deactivate employees whose leave date is today.
     */
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

package de.techdev.trackr.domain.employee.login;

import de.techdev.trackr.core.security.OAuth2ServerConfiguration;
import de.techdev.trackr.core.security.RemoveTokenService;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.EmployeeRepository;
import de.techdev.trackr.util.LocalDateUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Autowired
    private RemoveTokenService removeTokenService;

    /**
     * Deactivate employees whose leave date is today. Also remove OAuth tokens.
     */
    @Transactional
    public void deactivateEmployeesWithLeaveDateToday() {
        List<Employee> employeesToDeactivate = employeeRepository.findByLeaveDateAndCredential_Enabled(LocalDateUtil.fromLocalDate(LocalDate.now()), true);
        employeesToDeactivate.forEach(employee -> {
            log.info("Deactivating employee {}", employee);
            employee.getCredential().setEnabled(false);
            removeTokenService.removeTokens(OAuth2ServerConfiguration.TRACKR_PAGE_CLIENT, employee.getCredential().getEmail());
            employeeRepository.save(employee);
        });
    }
}

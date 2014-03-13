package de.techdev.trackr.employee;

import de.techdev.trackr.domain.Credential;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(Employee.class)
@Slf4j
public class EmployeeEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkCreateAuthority(Employee employee) {
        log.debug("Creating employee {}", employee);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkUpdateAuthority(Employee employee) {
        log.debug("Updating employee {}", employee);
        deactivateEmployeeIfNecessary(employee);
    }

    protected void deactivateEmployeeIfNecessary(Employee employee) {
        if(employee.getLeaveDate() != null && employee.getCredential().getEnabled()) {
            LocalDate today = LocalDate.now();
            Instant instant = Instant.ofEpochMilli(employee.getLeaveDate().getTime());
            LocalDate leaveDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
            if(leaveDate.isBefore(today) || leaveDate.equals(today)) {
                log.info("Deactivating employee {}", employee);
                employee.getCredential().setEnabled(false);
            }
        }
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkDeleteAuthority(Employee employee) {
        log.debug("Deleting employee {}", employee);
    }

    @HandleBeforeLinkSave
    @PreAuthorize("denyAll()")
    public void changeCredentialForbidden(Employee employee, Credential credential) {

    }

    @HandleBeforeLinkDelete
    @PreAuthorize("denyAll()")
    public void deleteCredentialForbidden(Employee employee) {

    }
}

package de.techdev.trackr.domain.employee;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

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
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkDeleteAuthority(Employee employee) {
        log.debug("Deleting employee {}", employee);
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("denyAll()")
    public void deleteCredentialForbidden(Employee employee) {
        //deny all, cannot be called
    }
}

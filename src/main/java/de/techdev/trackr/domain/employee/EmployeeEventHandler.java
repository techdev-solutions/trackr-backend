package de.techdev.trackr.domain.employee;

import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryEventHandler(Employee.class)
@SuppressWarnings("unused")
public class EmployeeEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkCreateAuthority(Employee employee) {
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void checkUpdateAuthority(Employee employee) {
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkDeleteAuthority(Employee employee) {
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("denyAll()")
    public void deleteCredentialForbidden(Employee employee) {
        //deny all, cannot be called
    }
}

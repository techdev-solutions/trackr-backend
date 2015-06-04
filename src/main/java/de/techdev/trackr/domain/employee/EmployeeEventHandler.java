package de.techdev.trackr.domain.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryEventHandler(Employee.class)
@SuppressWarnings("unused")
public class EmployeeEventHandler {

    @Autowired
    private SettingsRepository settingsRepository;

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkCreateAuthority(Employee employee) {
    }

    @HandleAfterCreate
    public void createInitialSettings(Employee employee) {
        Settings settings = new Settings();
        settings.setEmployee(employee);
        settings.setType(Settings.SettingsType.LOCALE);
        settings.setValue("de");
        settingsRepository.save(settings);
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

package de.techdev.trackr.domain.company;

import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(Company.class)
@SuppressWarnings("unused")
public class CompanyEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkSaveAuthority(Company company) {
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkUpdateAuthority(Company company) {
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkDeleteAuthority(Company company) {
    }

    @HandleBeforeLinkSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void beforeAddContactPerson(Company company, List<ContactPerson> contactPersons) {
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void beforeDeleteContactPerson(Company company) {
    }
}

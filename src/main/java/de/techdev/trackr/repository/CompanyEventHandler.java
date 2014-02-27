package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Company;
import de.techdev.trackr.domain.ContactPerson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(Company.class)
@Slf4j
public class CompanyEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkSaveAuthority(Company company) {
        log.debug("Creating company {}", company);
    }

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkUpdateAuthority(Company company) {
        log.debug("Updating company {}", company);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkDeleteAuthority(Company company) {
        log.debug("Deleting company {}", company);
    }

    @HandleBeforeLinkSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void beforeAddContactPerson(Company company, List<ContactPerson> contactPersons) {
        log.debug("Adding contact persons {} to company {}", contactPersons, company);
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void beforeDeleteContactPerson(Company company) {
        log.debug("Deleting linked entity from company, is now {}", company);
    }
}

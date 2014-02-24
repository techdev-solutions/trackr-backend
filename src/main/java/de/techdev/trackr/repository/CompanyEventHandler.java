package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Company;
import de.techdev.trackr.domain.ContactPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(Company.class)
public class CompanyEventHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkSaveAuthority(Company company) {
        logger.debug("Updating company {}", company);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkDeleteAuthority(Company company) {
        logger.debug("Deleting company {}", company);
    }

    @HandleBeforeLinkSave
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void beforeAddContactPerson(Company company, List<ContactPerson> contactPersons) {
        logger.debug("Adding contact persons {} to company {}", contactPersons, company);
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    public void beforeDeleteContactPerson(Company company) {
        logger.debug("Deleting linked entity from company, is now {}", company);
    }
}

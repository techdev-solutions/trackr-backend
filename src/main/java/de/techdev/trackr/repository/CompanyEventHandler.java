package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

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
}

package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Company;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(Company.class)
public class CompanyEventHandler {

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkSaveAuthority(Company company) {

    }
}

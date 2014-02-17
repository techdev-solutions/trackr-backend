package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Authority;
import de.techdev.trackr.domain.Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeLinkDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeLinkSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(Credential.class)
public class CredentialEventHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Only admins may add roles.
     *
     * @param credential  The credential object
     * @param authorities The authorities to set
     */
    @HandleBeforeLinkSave
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void beforeAuthoritySave(Credential credential, List<Authority> authorities) {
        logger.debug("Updating credential authorities for user {} to {}.", credential, authorities);
    }

    /**
     * Only admins may delete roles
     * @param credential The credentials to update
     */
    @HandleBeforeLinkDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void beforeAuthorityDelete(Credential credential) {
        logger.debug("Deleted linked object from credential, is now {}.", credential);
    }
}

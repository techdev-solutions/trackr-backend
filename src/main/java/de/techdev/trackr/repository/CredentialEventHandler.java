package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Authority;
import de.techdev.trackr.domain.Credential;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleBeforeLinkDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeLinkSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(Credential.class)
@Slf4j
public class CredentialEventHandler {

    /**
     * Only admins may add roles.
     *
     * @param credential  The credential object
     * @param authorities The authorities to set
     */
    @HandleBeforeLinkSave
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void beforeAuthoritySave(Credential credential, List<Authority> authorities) {
        log.debug("Updating credential authorities for user {} to {}.", credential, authorities);
    }

    /**
     * Only admins may delete roles
     * @param credential The credentials to update
     */
    @HandleBeforeLinkDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void beforeAuthorityDelete(Credential credential) {
        log.debug("Deleted linked object from credential, is now {}.", credential);
    }
}

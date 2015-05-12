package de.techdev.trackr.domain.project;

import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(Project.class)
@SuppressWarnings("unused")
public class ProjectEventHandler {

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkUpdatePermission(Project project) {
    }

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkCreatePermission(Project project) {
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkDeletePermission(Project project) {
    }

    @HandleBeforeLinkSave
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkCompanyPermission(Project project, Object link) {
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkLinkDeletePermission(Project project) {
    }
}

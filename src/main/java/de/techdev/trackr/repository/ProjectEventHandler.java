package de.techdev.trackr.repository;

import de.techdev.trackr.domain.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Moritz Schulze
 */
@RepositoryEventHandler(Project.class)
@Slf4j
public class ProjectEventHandler {

    @HandleBeforeSave
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkUpdatePermission(Project project) {
        log.debug("Updating project {}", project);
    }

    @HandleBeforeCreate
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkCreatePermission(Project project) {
        log.debug("Creating project {}", project);
    }

    @HandleBeforeDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkDeletePermission(Project project) {
        log.debug("Deleting project {}", project);
    }

    @HandleBeforeLinkSave
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkCompanyPermission(Project project, Object link) {
        log.debug("Changing links on project {}", project);
    }

    @HandleBeforeLinkDelete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void checkLinkDeletePermission(Project project) {
        log.debug("Deleting link from project {}", project);
    }
}

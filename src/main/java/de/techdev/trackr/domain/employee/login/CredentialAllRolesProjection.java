package de.techdev.trackr.domain.employee.login;

import org.springframework.data.rest.core.config.Projection;

/**
 * @author Moritz Schulze
 */
@Projection(types = Credential.class, name = "allRolesOverview")
public interface CredentialAllRolesProjection {
    Long getId();

    String getEmail();

    java.util.List<Authority> getAuthorities();
}

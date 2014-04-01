package de.techdev.trackr.core;

import de.techdev.trackr.core.security.MethodSecurityConfiguration;
import de.techdev.trackr.core.security.SecurityConfiguration;
import de.techdev.trackr.domain.JpaConfiguration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Register the Spring Security filter chain.
 * <p>
 * Loads the {@link de.techdev.trackr.domain.JpaConfiguration} as it is needed in {@link de.techdev.trackr.domain.employee.login.TrackrUserDetailsService}
 *
 * @author Moritz Schulze
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(SecurityConfiguration.class, JpaConfiguration.class, MethodSecurityConfiguration.class);
    }
}

package de.techdev.trackr;

import de.techdev.trackr.repository.JpaConfiguration;
import de.techdev.trackr.security.MethodSecurityConfiguration;
import de.techdev.trackr.security.SecurityConfiguration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Register the Spring Security filter chain.
 * <p>
 * Loads the {@link de.techdev.trackr.repository.JpaConfiguration} as it is needed in {@link de.techdev.trackr.security.TrackrUserDetailsService}
 *
 * @author Moritz Schulze
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(SecurityConfiguration.class, JpaConfiguration.class, MethodSecurityConfiguration.class, ScheduledJobsConfiguration.class);
    }
}

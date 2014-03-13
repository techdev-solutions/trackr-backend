package de.techdev.trackr.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * This class enables global method security via {@link org.springframework.security.access.prepost.PreAuthorize} in JPA Repositories
 * and other controllers. It also enables the role hierarchy in them.
 *
 * It gets loaded in the security web application {@link de.techdev.trackr.core.SecurityWebApplicationInitializer} for JPA Repositories
 * and in {@link de.techdev.trackr.core.ApiWebApplicationInitializer} for all other API controllers.
 *
 * @author Moritz Schulze
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    /**
     * This is needed so {@link org.springframework.security.access.prepost.PreAuthorize} and so on know the role hierarchy.
     */
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler methodSecurityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
        methodSecurityExpressionHandler.setRoleHierarchy(roleHierarchy);
        return methodSecurityExpressionHandler;
    }

    @Autowired
    private RoleHierarchy roleHierarchy;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return authenticationManager;
    }
}

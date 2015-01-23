package de.techdev.trackr.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * This class enables global method security via {@link org.springframework.security.access.prepost.PreAuthorize} in JPA Repositories
 * and other controllers. It also enables the role hierarchy in them.
 */
@Profile("granular-security")
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * This is needed so {@link org.springframework.security.access.prepost.PreAuthorize} and so on know the role hierarchy.
     */
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler methodSecurityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
        methodSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());

        //Needs to be done so we can access beans in security expressions
        methodSecurityExpressionHandler.setApplicationContext(applicationContext);
        return methodSecurityExpressionHandler;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        //TODO make this configurable
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_SUPERVISOR ROLE_SUPERVISOR > ROLE_EMPLOYEE ROLE_EMPLOYEE > ROLE_ANONYMOUS");
        return roleHierarchy;
    }
    @Bean
    public RoleVoter roleVoter() {
        return new RoleHierarchyVoter(roleHierarchy());
    }
}

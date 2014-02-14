package de.techdev.trackr;

import de.techdev.trackr.repository.JpaConfiguration;
import de.techdev.trackr.security.SecurityConfiguration;
import de.techdev.trackr.web.ApiWebMvcConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Creates the dispatcher servlet with the correct configuration classes.
 * @author Moritz Schulze
 */
public class ApiWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {SecurityConfiguration.class, JpaConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {ApiWebMvcConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/api/*"};
    }

    @Override
    protected String getServletName() {
        return "api-dispatcher";
    }
}

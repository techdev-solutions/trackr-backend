package de.techdev.trackr;

import de.techdev.trackr.security.MethodSecurityConfiguration;
import de.techdev.trackr.web.ApiWebMvcConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Creates the api-dispatcher for the API servlet with the correct configuration classes.
 * @author Moritz Schulze
 */
public class ApiWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        //The global method security is only in the root context, to protect RequestMappings in custom controllers
        //we load the the MethodSecurityConfiguration here, too.
        return new Class<?>[] {ApiWebMvcConfiguration.class, MethodSecurityConfiguration.class};
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

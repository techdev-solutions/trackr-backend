package de.techdev.trackr.core;

import de.techdev.trackr.core.security.MethodSecurityConfiguration;
import de.techdev.trackr.core.web.api.ApiWebMvcConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletRegistration;

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

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("dispatchOptionsRequest", "true");
    }
}

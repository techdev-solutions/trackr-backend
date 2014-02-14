package de.techdev.trackr;

import de.techdev.trackr.web.AppWebMvcConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Creates the dispatcher servlet with the correct configuration classes.
 * @author Moritz Schulze
 */
public class AppWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        //TODO: this should also include the Security configuration for the app, but currently this fails
        return new Class<?>[] {};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {AppWebMvcConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}

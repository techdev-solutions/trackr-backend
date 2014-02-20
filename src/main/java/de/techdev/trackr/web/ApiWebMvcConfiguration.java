package de.techdev.trackr.web;

import de.techdev.trackr.domain.Authority;
import de.techdev.trackr.domain.Credential;
import de.techdev.trackr.domain.Employee;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

/**
 * @author Moritz Schulze
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "de.techdev.trackr.web.api")
public class ApiWebMvcConfiguration extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(new Class[] {Employee.class, Credential.class, Authority.class});
        config.setReturnBodyOnUpdate(true);
        config.setReturnBodyOnCreate(true);
    }

    @Override
    protected void configureExceptionHandlerExceptionResolver(ExceptionHandlerExceptionResolver exceptionResolver) {
        exceptionResolver.setOrder(1);
    }
}

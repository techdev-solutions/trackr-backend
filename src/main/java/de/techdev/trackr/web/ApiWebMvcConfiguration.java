package de.techdev.trackr.web;

import de.techdev.trackr.domain.Employee;
import de.techdev.trackr.security.MethodSecurityConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Moritz Schulze
 */
@Configuration
@EnableWebMvc
@Import(MethodSecurityConfiguration.class) //for global method security to work in a webmvc context we need to have access to the authentiacionManager, this config does that
@EnableGlobalMethodSecurity(prePostEnabled = true) //this enables security annotations for all extra controllers in the api (i.e. not spring-data-rest)
@ComponentScan(basePackages = "de.techdev.trackr.web.api")
public class ApiWebMvcConfiguration extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(new Class[] {Employee.class});
    }
}

package de.techdev.trackr.web;

import de.techdev.trackr.security.MethodSecurityConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Moritz Schulze
 */
@Configuration
@EnableWebMvc
@Import(MethodSecurityConfiguration.class)
@ComponentScan(basePackages = "de.techdev.trackr.web.api")
public class ApiWebMvcConfiguration extends RepositoryRestMvcConfiguration {

}

package de.techdev.trackr.web;

import de.techdev.trackr.domain.*;
import de.techdev.trackr.repository.AddressEventHandler;
import de.techdev.trackr.repository.CompanyEventHandler;
import de.techdev.trackr.repository.ContactPersonEventHandler;
import de.techdev.trackr.repository.CredentialEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.AnnotatedHandlerBeanPostProcessor;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Moritz Schulze
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "de.techdev.trackr.web.api")
public class ApiWebMvcConfiguration extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(new Class[] {Employee.class, Credential.class, Authority.class, Company.class, ContactPerson.class, Address.class});
        config.setReturnBodyOnUpdate(true);
        config.setReturnBodyOnCreate(true);
    }

    @Bean
    public AddressEventHandler addressEventHandler() {
        return new AddressEventHandler();
    }

    @Bean
    public ContactPersonEventHandler contactPersonEventHandler() {
        return new ContactPersonEventHandler();
    }

    @Bean
    public CompanyEventHandler companyEventHandler() {
        return new CompanyEventHandler();
    }

    @Bean
    public CredentialEventHandler credentialEventHandler() {
        return new CredentialEventHandler();
    }

    /**
     * This bean is needed for the repository event handlers to work.
     */
    @Bean
    public AnnotatedHandlerBeanPostProcessor repositoryEventListener() {
        return new AnnotatedHandlerBeanPostProcessor();
    }
}

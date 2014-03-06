package de.techdev.trackr.web;

import de.techdev.trackr.domain.*;
import de.techdev.trackr.repository.*;
import de.techdev.trackr.web.converters.DateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.format.FormatterRegistry;
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
        config.exposeIdsFor(new Class[] {Employee.class, Credential.class, Authority.class, Company.class, ContactPerson.class, Address.class, Project.class, WorkTime.class});
        config.setReturnBodyOnUpdate(true);
        config.setReturnBodyOnCreate(true);
    }

    @Bean
    public WorkTimeEventHandler workTimeEventHandler() {
        return new WorkTimeEventHandler();
    }

    @Bean
    public ProjectEventHandler projectEventHandler() {
        return new ProjectEventHandler();
    }

    @Bean
    public EmployeeEventHandler employeeEventHandler() {
        return new EmployeeEventHandler();
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

    @Bean
    public DateConverter dateConverter() {
        return new DateConverter();
    }

    @Override
    protected void configureConversionService(ConfigurableConversionService conversionService) {
        super.configureConversionService(conversionService);
        conversionService.addConverter(dateConverter());
    }

    /**
     * Somehow the "normal" Spring MVC (not spring-data-rest) does not use the converter registered in {@link #configureConversionService} so we have to register it again.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(dateConverter());
    }
}
package de.techdev.trackr.core.web.api;

import de.techdev.trackr.core.web.converters.DateConverter;
import de.techdev.trackr.domain.common.EmployeeSettingsLocaleResolver;
import de.techdev.trackr.domain.company.Address;
import de.techdev.trackr.domain.company.Company;
import de.techdev.trackr.domain.company.ContactPerson;
import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.employee.expenses.TravelExpense;
import de.techdev.trackr.domain.employee.expenses.reports.Report;
import de.techdev.trackr.domain.employee.expenses.reports.comments.Comment;
import de.techdev.trackr.domain.employee.sickdays.SickDays;
import de.techdev.trackr.domain.employee.vacation.VacationRequest;
import de.techdev.trackr.domain.project.Project;
import de.techdev.trackr.domain.project.billtimes.BillableTime;
import de.techdev.trackr.domain.project.invoice.Invoice;
import de.techdev.trackr.domain.project.worktimes.WorkTime;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;

@Configuration
public class ApiWebMvcConfiguration extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Employee.class, Company.class, ContactPerson.class,
                Address.class, Project.class, WorkTime.class, BillableTime.class, VacationRequest.class, TravelExpense.class,
                Report.class, Comment.class, Invoice.class, SickDays.class);
        config.setReturnBodyOnUpdate(true);
        config.setReturnBodyOnCreate(true);
    }

    /**
     * The self HREF for entities contains {?projection} to indiciate the parameter is available, but we don't want that.
     * The {@link de.techdev.trackr.core.web.api.RepositoryEntityLinksWithoutProjection} switches {?projection} off.
     */
    @Override
    @Bean
    public RepositoryEntityLinks entityLinks() {
        return new RepositoryEntityLinksWithoutProjection(repositories(), resourceMappings(), config(), pageableResolver(), backendIdConverterRegistry());
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new EmployeeSettingsLocaleResolver();
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

    /**
     * Needed for mapping exceptions in jackson that otherwise would get an ugly error message.
     */
    @Bean
    public JsonMappingHandlerExceptionResolver jsonMappingHandlerExceptionResolver() {
        return new JsonMappingHandlerExceptionResolver();
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(jsonMappingHandlerExceptionResolver());
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }

    /**
     * Load all messages, reload when locale changes.
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:org/hibernate/validator/ValidationMessages", "classpath:/i18n/validation/messages");
        return messageSource;
    }

    /**
     * Add the validator to spring data rest.
     */
    @Override
    protected void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("beforeSave", validator());
        validatingListener.addValidator("beforeCreate", validator());
    }

    /**
     * Custom validator that extracts messages with locale. Used by spring-data-rest.
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource());
        return localValidatorFactoryBean;
    }

}